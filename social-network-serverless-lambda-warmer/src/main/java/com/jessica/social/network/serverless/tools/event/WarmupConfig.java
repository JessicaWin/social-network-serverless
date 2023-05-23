package com.jessica.social.network.serverless.tools.event;

import lombok.Data;

@Data
public class WarmupConfig {
	private boolean skip = false;
	private int concurrency = 5;
	private Object payload;
}
