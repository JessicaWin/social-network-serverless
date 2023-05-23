package com.jessica.social.network.serverless.tools.handler;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.AwsEnvVarOverrideRegionProvider;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jessica.social.network.serverless.tools.event.ScheduledEvent;
import com.jessica.social.network.serverless.tools.event.WarmupConfig;

public class LambdaWarmer implements RequestHandler<ScheduledEvent, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LambdaWarmer.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/* get function configuration */
	private static final String stage;
	private static final String variant;
	private static final AWSLambdaAsync lambdaClient;

	static {
		String region = new AwsEnvVarOverrideRegionProvider().getRegion();
		lambdaClient = AWSLambdaAsyncClientBuilder.standard().withRegion(region).build();
		stage = Optional.ofNullable(System.getenv().get("stage")).orElse("develop");
		variant = Optional.ofNullable(System.getenv().get("variant")).orElse("");
	}

	@Override
	public Object handleRequest(ScheduledEvent event, Context context) {
		LOGGER.info("Start to warm up functions");
		LOGGER.debug("Cloud Watch Event: {}", event);

		Map<String, WarmupConfig> configs = event.getDetail();
		configs.forEach(this::asyncWarmingUp);

		LOGGER.info("Functions warmed up");
		return null;
	}

	// to warm up functions one by one
	private void asyncWarmingUp(String name, WarmupConfig config) {
		String functionName = stage + "-" + variant + "-" + name;
		LOGGER.debug("Config {}", config);
		if (config.isSkip()) {
			LOGGER.info("Skip function: {}", functionName);
			return;
		}

		try {
			LOGGER.info("Warming up function: {}", functionName);
			int concurrency = config.getConcurrency();
			ByteBuffer payload = ByteBuffer.wrap(OBJECT_MAPPER.writeValueAsBytes(config.getPayload()));

			for (int i = concurrency; i > 0; i--) {
				LOGGER.debug("request No.{}", (concurrency - i + 1));
				InvokeRequest request = new InvokeRequest().withInvocationType(InvocationType.Event)
						.withFunctionName(functionName).withPayload(payload);
				InvokeResult result = lambdaClient.invoke(request);
				LOGGER.debug(result.toString());
			}
		} catch (Exception e) {
			LOGGER.error("Failed to warm up lambda function {}", functionName, e);
		}
	}
}
