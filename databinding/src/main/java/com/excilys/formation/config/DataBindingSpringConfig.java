package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class allowing Spring to scan the current package for all beans.
 */
@Configuration
@ComponentScan(basePackages = "com.excilys.formation.mapper")
public class DataBindingSpringConfig {
}