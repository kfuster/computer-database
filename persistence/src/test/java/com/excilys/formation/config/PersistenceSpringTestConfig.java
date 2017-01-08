package com.excilys.formation.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
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

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.excilys.formation.persistence")
@PropertySource("config.properties")
public class PersistenceSpringTestConfig {
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

    @Bean(name="dbUnitDatabaseConfig")
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setDatatypeFactory(new MySqlDataTypeFactory());
        databaseConfigBean.setMetadataHandler(new MySqlMetadataHandler());
        return databaseConfigBean;
    }

    @Bean(name="dbUnitDatabaseConnection")
    public DatabaseDataSourceConnectionFactoryBean dataSourceDbUnit() {
        DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean(dataSource());
        databaseDataSourceConnectionFactoryBean.setSchema("computertest");
        databaseDataSourceConnectionFactoryBean.setDatabaseConfig(databaseConfigBean());
        return databaseDataSourceConnectionFactoryBean;
    }
    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }
}