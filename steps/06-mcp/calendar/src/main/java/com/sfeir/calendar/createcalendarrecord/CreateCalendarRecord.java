package com.sfeir.calendar.createcalendarrecord;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.sfeir.calendar.domain.model.CalendarRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCalendarRecord {

    private final CreateRecord createRecord;

    @Tool(description = "Create a new calendar item")
    public void createCalendarItem(@ToolParam(description = "The date of the calendar item in YYYY-MM-DD format") String day,
            @ToolParam(description = "The start time of the calendar item in HH:mm format") String from,
            @ToolParam(description = "The end time of the calendar item in HH:mm format") String to,
            @ToolParam(description= "The description of the calendar item") String description) {
        
                LocalTime fromTime = LocalTime.parse(from);
        LocalTime toTime = LocalTime.parse(to);
        LocalDate parsedDate = LocalDate.parse(day);
        CalendarRecord calendarRecord = new CalendarRecord(parsedDate, fromTime, toTime, description);
        createRecord.create(calendarRecord);
        log.info("Calendar item created successfully");
    }
}
