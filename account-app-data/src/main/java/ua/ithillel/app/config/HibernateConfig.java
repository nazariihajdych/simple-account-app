package ua.ithillel.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:psql_db.properties")
@EnableTransactionManagement
@EnableJpaRepositories(value = "ua.ithillel.app")
public class HibernateConfig {
    private final Environment environment;

    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean entityManagerFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ua.ithillel.app");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

        return dataSource;
    }

        private Properties hibernateProperties(){
        Properties properties = new Properties();
            properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
            properties.put("hibernate.default_schema", environment.getRequiredProperty("hibernate.default_schema"));
            properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
            properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
            properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

        return properties;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
