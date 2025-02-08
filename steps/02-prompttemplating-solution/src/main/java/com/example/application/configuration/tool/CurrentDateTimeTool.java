package com.example.application.configuration.tool;

import java.time.ZonedDateTime;
import java.util.function.Function;


public class CurrentDateTimeTool implements Function<CurrentDateTimeTool.Request, CurrentDateTimeTool.Response> {

    @Override
    public Response apply(Request request) {
        ZonedDateTime now = ZonedDateTime.now();
        return new Response(
                now.toLocalDate().toString(),
                now.toLocalTime().toString(),
                now.getDayOfWeek().name(),
                String.valueOf(now.getYear()));
    }

    public record Request() {
    }

    public record Response(String currentDate, String currentTime, String dayOfWeek, String year) {
    }
}
