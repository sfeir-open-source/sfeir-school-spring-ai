package com.sfeir.calendar;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class CurrentTime {

    @Tool(description = "Get the current date and time in ISO-8601 format")
    public static String getCurrentDateAndTime() {
        return Instant.now().atZone(ZoneId.systemDefault()).toString();
    }
}
