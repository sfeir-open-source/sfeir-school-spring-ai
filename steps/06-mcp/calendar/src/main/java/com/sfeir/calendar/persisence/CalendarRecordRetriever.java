package com.sfeir.calendar.persisence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sfeir.calendar.domain.model.CalendarRecord;
import com.sfeir.calendar.listrecords.RetrieveRecords;
import com.sfeir.calendar.persisence.entity.CalendarItemJpaEntity;
import com.sfeir.calendar.persisence.repository.CalendarItemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalendarRecordRetriever implements RetrieveRecords {

    private final CalendarItemRepository repository;

    public CalendarRecordRetriever(CalendarItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CalendarRecord> getAll() {
        return repository.findAll().stream()
                .peek(entity -> log.info("Found calendar item: {}", entity))
                .map(this::mapToCalendarRecord)
                .toList();
    }

    private CalendarRecord mapToCalendarRecord(CalendarItemJpaEntity entity) {
        LocalDate day = LocalDate.parse(entity.getDetail().get("day"));
        LocalTime from = LocalTime.parse(entity.getDetail().get("from"));
        LocalTime to = LocalTime.parse(entity.getDetail().get("to"));
        String description = entity.getDetail().get("description");
        
        return new CalendarRecord(day, from, to, description);
    }
}
