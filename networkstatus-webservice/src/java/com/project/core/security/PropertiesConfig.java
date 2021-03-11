/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author SAM
 */
@Configuration
@PropertySource("classpath:config.properties")
public class PropertiesConfig {

    //@Inject
    @Autowired
    private Environment environment;

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key) {
        String value = environment.getProperty(key);
        System.out.println("Read From Properties File : Key =" + key + " value=" + value);
        return value;
    }

    @Bean(name = "propertiesEnvironment")
    public Environment environment() {
        return getEnvironment();
    }
}
