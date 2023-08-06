package com.myadd.myadd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyaddApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyaddApplication.class, args);
	}
}
