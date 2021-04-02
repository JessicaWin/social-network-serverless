package com.jessica.social.network.serverless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@ServletComponentScan(basePackages = {"com.jessica"})
@ComponentScan(basePackages = {"com.jessica"})
public class SocialNetworkServerlessWebApplication {

	public static void main(String[] args) {

		SpringApplication.run(SocialNetworkServerlessWebApplication.class, args);
	}

}
