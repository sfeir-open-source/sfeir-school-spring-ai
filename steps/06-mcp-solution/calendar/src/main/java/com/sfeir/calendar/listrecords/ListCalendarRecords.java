package com.sfeir.calendar.listrecords;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.sfeir.calendar.domain.model.CalendarRecord;

@Service
public class ListCalendarRecords {

  private final RetrieveRecords retrieveRecords;
  
  public ListCalendarRecords(RetrieveRecords retrieveRecords) {
    this.retrieveRecords = retrieveRecords;
  }

  @Tool(description = "List the user agenda, calendar and meeting items, tasks and appointments")
  public List<CalendarRecord> listCalendarItems() {
    return retrieveRecords.getAll();
  }
}
