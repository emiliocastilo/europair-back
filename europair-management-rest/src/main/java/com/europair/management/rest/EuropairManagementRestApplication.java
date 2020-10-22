package com.europair.management.rest;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity
@EnableFeignClients(basePackages = {"com.europair.management.impl.integrations"})
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@ComponentScan(basePackages = "com.europair.management")
public class EuropairManagementRestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(EuropairManagementRestApplication.class, args);
	}

}
