package com.jessica.social.network.serverless.tools.event;

import java.util.Map;
import lombok.Data;

@Data
public class ScheduledEvent {
  private Map<String, WarmupConfig> detail;
}
