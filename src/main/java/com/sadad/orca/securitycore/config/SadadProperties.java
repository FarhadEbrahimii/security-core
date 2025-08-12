package com.sadad.orca.securitycore.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Mahdad Aioby
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sadad", ignoreUnknownFields = true)
public class SadadProperties {

    private final Metrics metrics = new Metrics();
    private final Security security = new Security();
    private final Logging logging = new Logging();

    @Data
    public static class Metrics {

        private final Logs logs = new Logs();

        @Data
        public static class Logs {

            private boolean enabled = false;

            private long reportFrequency = 60;
        }
    }
    @Data
    public static class Logging {

        private final Logstash logstash = new Logstash();
        private final SpectatorMetrics spectatorMetrics = new SpectatorMetrics();

        @Data
        public static class Logstash {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 5000;

            private int queueSize = 512;

        }

        @Data
        public static class SpectatorMetrics {

            private boolean enabled = false;

        }
    }
    @Data
    public static class Security {

        private final Feign feign = new Feign();
        private final ClientAuthorization clientAuthorization = new ClientAuthorization();
    }

    @Data
    public static class ClientAuthorization {

        private String accessTokenUri;

        private String tokenServiceId;

        private String clientId;

        private String clientSecret;

        private List<String> scopes;
    }
    @Data
    public static class Feign {
        private boolean enabled = true;
    }
}
