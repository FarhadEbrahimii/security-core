package com.sadad.orca.securitycore.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadad.orca.securitycore.config.OAuth2ClientProperties;
import com.sadad.orca.securitycore.exceptions.ErrorDefinition;
import com.sadad.orca.securitycore.exceptions.SadadException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdad Aioby
 */
@Slf4j
public class SecurityUtil {

    public static String extractClientId(String token) {
        String[] parts = token.split("\\.");
        if (parts.length < 2) {
            throw new JwtException("Invalid JWT structure");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(payload);
        } catch (IOException e) {
            throw new JwtException("Failed to parse JWT payload", e);
        }

        if (!jsonNode.has("aud")) {
            throw new JwtException("Missing client_id claim in JWT");
        }

        return jsonNode.get("aud").asText();
    }

    public static Map getClaims(String accessToken, List<OAuth2ClientProperties.ClientConfig> clients) {
        try {
            String tokenClientId = extractClientId(accessToken);
            String secretKey = clients
                    .stream()
                    .filter(client -> !client.getClientId().isEmpty())
                    .filter(client -> client.getClientId().equalsIgnoreCase(tokenClientId))
                    .findFirst()
                    .map(OAuth2ClientProperties.ClientConfig::getClientSecret)
                    .orElse(null);
            assert secretKey != null;
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            return Jwts.parser()
                    .verifyWith(key)
                    .clockSkewSeconds(1749594278032844L)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

        } catch (UnsupportedJwtException e) {
            log.error(accessToken);
            throw new SadadException(ErrorDefinition.INVALID_TOKEN_EXCEPTION);
        } catch (ExpiredJwtException e) {
            log.error(accessToken);
            throw new SadadException(ErrorDefinition.EXPIRED_TOKEN_EXCEPTION);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.error(accessToken);
            throw new SadadException(ErrorDefinition.ILLEGAL_ARGUMENT_OR_SIGNATURE);
        } catch (Exception e) {
            log.error(accessToken);
            throw new SadadException(ErrorDefinition.INVALID_TOKEN_EXCEPTION);
        }
    }
}
