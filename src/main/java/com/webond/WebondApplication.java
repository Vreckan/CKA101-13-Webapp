package com.webond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
	    scanBasePackages = {
	        "com.webond",
	        "com.service",
	        "com.servicetype",
	        "com.serviceslot"
	    },
	    exclude = {
	        HibernateJpaAutoConfiguration.class,
	        JpaRepositoriesAutoConfiguration.class
	    }
	)
//@EntityScan(basePackages = {
//        "com.service.model",
//        "com.servicetype.model",
//        "com.serviceslot.model"
//})
//@EnableJpaRepositories(basePackages = {
//        "com.service.model",
//        "com.servicetype.model",
//        "com.serviceslot.model"
//})
public class WebondApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebondApplication.class, args);
    }
}