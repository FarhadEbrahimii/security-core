package com.sadad.orca.securitycore.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CustomBasicErrorController extends BasicErrorController {
    public static final String MESSAGE_FORMAT_WITH_FOR = "%s for %s";
    public static final String MESSAGE_FORMAT = "%s: %s";
    protected final Log logger = LogFactory.getLog(getClass());

    public CustomBasicErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    public CustomBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public CustomBasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }


    @RequestMapping
    @ResponseBody
    @Override
    public ResponseEntity error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);
        switch (status) {
            case NOT_FOUND: {
                return sendNoHandlerFoundError(body);
            }
            default: {
                return sendInternalServerError(body);
            }
        }
    }

    private ResponseEntity sendNoHandlerFoundError(Map<String, Object> body) {
        ErrorDefinition errorDefinition = ErrorDefinition.HANDLER_NOT_FOUND_EXCEPTION;
        logger.debug(errorDefinition.getMessage());
        return ResponseUtils.createResponseEntity(errorDefinition, String.format(MESSAGE_FORMAT_WITH_FOR, errorDefinition.getMessage(), String.valueOf(body.get("path"))), HttpStatus.valueOf(errorDefinition.getHttpCode()));
    }

    private ResponseEntity sendInternalServerError(Map<String, Object> body) {
        ErrorDefinition errorDefinition = ErrorDefinition.INTERNAL_SERVER_ERROR_EXCEPTION;
        return ResponseUtils.createResponseEntity(errorDefinition, errorDefinition.getMessage(), HttpStatus.valueOf(errorDefinition.getHttpCode()));
    }
}
