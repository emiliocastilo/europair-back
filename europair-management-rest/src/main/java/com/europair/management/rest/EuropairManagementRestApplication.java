package com.europair.management.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.europair.management.impl.integrations"})
@ComponentScan(basePackages = "com.europair.management")
public class EuropairManagementRestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(EuropairManagementRestApplication.class, args);
	}

}
