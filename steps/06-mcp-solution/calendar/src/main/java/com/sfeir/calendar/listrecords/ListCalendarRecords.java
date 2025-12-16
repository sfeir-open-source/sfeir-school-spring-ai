package com.sfeir.calendar.listrecords;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.sfeir.calendar.domain.model.CalendarRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCalendarRecords {

  private final RetrieveRecords retrieveRecords;

  @Tool(name="listCalendarItems", description = "List the user agenda, calendar and meeting items, tasks and appointments. No parameters needed.")
  public List<CalendarRecord> listCalendarItems() {
    return retrieveRecords.getAll();
  }
}
