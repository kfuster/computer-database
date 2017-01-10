package com.excilys.formation.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by kfuster on 31/12/2016.
 * Class configuring the different Spring beans in this module.
 */

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.excilys.formation.persistence")
@PropertySource("classpath:config.properties")
public class PersistenceSpringConfig {

    @Value("${hbm.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${dataSource.jdbcUrl}")
    private String jdbcUrl;

    @Value("${dataSource.user}")
    private String username;

    @Value("${dataSource.password}")
    private String password;

    @Value("${dataSource.idleTimeout}")
    private Long idleTimeout;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory
                .setPackagesToScan("com.excilys.formation.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDataSourceClassName(dataSourceClassName);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setMaximumPoolSize(15);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.addDataSourceProperty("url", jdbcUrl);
        return dataSource;
    }

    /**
     * Method that return hibernateProperties.
     * @return Properties containing the new properties.
     */
    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }
}