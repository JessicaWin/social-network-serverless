package com.jessica.social.network.serverless.lambda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiGatewayRequest {
	private String flag;
}
