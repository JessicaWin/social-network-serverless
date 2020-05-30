package com.jessica.social.network.serverless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAspectJAutoProxy
public class SocialNetworkServerlessWebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(SocialNetworkServerlessWebApplication.class, args);
	}

}
