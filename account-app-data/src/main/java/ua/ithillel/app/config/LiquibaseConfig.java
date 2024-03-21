package ua.ithillel.app.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:psql_db.properties")
public class LiquibaseConfig {

    private final DataSource dataSource;
    private final Environment environment;

    public LiquibaseConfig(DataSource dataSource, Environment environment) {
        this.dataSource = dataSource;
        this.environment = environment;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-root.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(environment.getRequiredProperty("liquibase.default_schema"));
        return liquibase;
    }
}
