package com.gae;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gae.mapper")
public class DolbosigaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolbosigaeApplication.class, args);
	}

}
