package com.sfeir.calendar.configuration;

import com.sfeir.calendar.listrecords.ListCalendarRecords;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class McpConfig {

  @Bean
  public ToolCallbackProvider toolCallbackProvider(final ListCalendarRecords listCalendarRecords) {
    return MethodToolCallbackProvider.builder().toolObjects(listCalendarRecords).build();
  }
}
