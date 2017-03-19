package com.wuhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StartService1 {

	public static void main(String[] args) {
		SpringApplication.run(StartService1.class, args);
	}
}
