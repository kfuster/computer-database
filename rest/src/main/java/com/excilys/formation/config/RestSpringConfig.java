package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Ookami on 03/01/2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.formation.controller, com.excilys.formation.config")
public class RestSpringConfig {
}