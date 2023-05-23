package com.jessica.social.network.serverless.lambda.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.jessica.social.network.serverless.SocialNetworkServerlessWebApplication;
import com.jessica.social.network.serverless.lambda.model.ApiGatewayRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamLambdaHandler implements RequestStreamHandler {
	private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	static {
		try {
			handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>().defaultProxy().asyncInit()
					.springBootApplication(SocialNetworkServerlessWebApplication.class).buildAndInitialize();
		} catch (ContainerInitializationException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not initialize Spring Boot application", e);
		}
	}

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		String input = IOUtils.toString(inputStream);
		ApiGatewayRequest request = this.readToObject(input, ApiGatewayRequest.class);
		if ("warmer".equals(request.getFlag())) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("Empty return on warmup event");
			outputStream.close();
			return;
		}
		handler.proxyStream(new ByteArrayInputStream(input.getBytes()), outputStream, context);
	}

	private <T> T readToObject(String json, Class<T> clazz) {
		if (Strings.isNullOrEmpty(json)) {
			return null;
		}
		try {
			return new ObjectMapper().readValue(json, clazz);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid json string:" + json + ", for class: " + clazz.toString(), e);
		}
	}
}
