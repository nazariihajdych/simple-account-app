package ua.loggable.starter.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "loggable")
public class LoggerProperties {
    private boolean enabled;
}
