package com.excilys.formation.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.formation.service, com.excilys.formation.config")
public class ServiceSpringTestConfig {

    @Bean(name="dbUnitDatabaseConfig")
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setDatatypeFactory(new MySqlDataTypeFactory());
        databaseConfigBean.setMetadataHandler(new MySqlMetadataHandler());
        return databaseConfigBean;
    }

    @Autowired
    @Bean(name="dbUnitDatabaseConnection")
    public DatabaseDataSourceConnectionFactoryBean dataSourceDbUnit(DataSource dataSource) {
        DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        databaseDataSourceConnectionFactoryBean.setSchema("computertest");
        databaseDataSourceConnectionFactoryBean.setDatabaseConfig(databaseConfigBean());
        return databaseDataSourceConnectionFactoryBean;
    }
}