package ua.loggable.starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.loggable.starter.aspect.LoggableAspect;

@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
@ConditionalOnProperty(prefix = "loggable", name = "enabled", havingValue = "true")
public class LoggerAutoConfiguration {
    @Bean
    public LoggableAspect loggableAspect(){
        return new LoggableAspect();
    }
}
