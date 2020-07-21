package com.europair.management.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Properties;

@SpringBootApplication
public class EuropairManagementRestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EuropairManagementRestApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder
				.sources(EuropairManagementRestApplication.class)
				.properties(getProperties());
	}

	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "classpath:europair/");
		return props;
	}


}
