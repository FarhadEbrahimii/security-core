package com.sadad.orca.securitycore.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Mahdad Aioby
 */

public class CustomOAuth2ExpressionParser implements ExpressionParser {
    protected final Log logger = LogFactory.getLog(getClass());
    private final ExpressionParser delegate;
    private final OAuth2ClientProperties properties;

    public CustomOAuth2ExpressionParser(ExpressionParser delegate,
                                        OAuth2ClientProperties properties) {
        Assert.notNull(delegate, "Delegate parser cannot be null");
        this.delegate = delegate;
        this.properties = properties;
    }

    @Override
    public Expression parseExpression(String expressionString) {
        return delegate.parseExpression(processExpression(expressionString));
    }

    @Override
    public Expression parseExpression(String expressionString, ParserContext context) {
        return delegate.parseExpression(processExpression(expressionString), context);
    }
    private String processExpression(String expressionString) {
        Pattern pattern = Pattern.compile("(#oauth2\\.hasScope|oauth2HasScope)\\('([^)]+)'\\)");
        Matcher matcher = pattern.matcher(expressionString);

        if (matcher.find()) {
            String original = matcher.group(0);
            String methodType = matcher.group(1);
            String keys = matcher.group(2);
            StringBuilder expanded = new StringBuilder("(");
            boolean firstScope = true;

            for (String key : keys.split(",")) {
                String scopes = properties.getScopes().get(key.trim());
                if (scopes == null) {
                    logger.warn("Scope key '" + key + "' not found in configurations");
                    continue;
                }

                for (String scope : scopes.split(",")) {
                    if (!firstScope) {
                        expanded.append(" or ");
                    }
                    expanded.append("oauth2HasScope('").append(scope.trim()).append("')");
                    firstScope = false;
                }
            }

            if (expanded.length() == 1) {
                return expressionString;
            }

            expanded.append(")");
            return expressionString.replace(original, expanded.toString());
        }
        return expressionString;
    }
}