package com.sfeir.calendar.configuration;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sfeir.calendar.createcalendarrecord.CreateCalendarRecord;
import com.sfeir.calendar.listrecords.ListCalendarRecords;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(final ListCalendarRecords listCalendarRecords, final CreateCalendarRecord createCalendarRecord) {
        return MethodToolCallbackProvider.builder().toolObjects(listCalendarRecords, createCalendarRecord).build();
    }
}
