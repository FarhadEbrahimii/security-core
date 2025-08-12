package com.sadad.orca.securitycore.dto.response;

import lombok.Data;

/**
 * @author Mahdad Aioby
 */
@Data
public class TokenResponse {
    private String last_logins;
    private String token_type;
    private Integer expires_in;
    private String access_token;
}
