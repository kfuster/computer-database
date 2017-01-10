package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Class configuring the different Spring beans for this module.
 */
@Configuration
@ComponentScan(basePackages = "com.excilys.formation.service")
public class ServiceSpringConfig {
}