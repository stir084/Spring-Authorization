package com.example.SpringAuthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthorizationApplication.class, args);
	}

}
