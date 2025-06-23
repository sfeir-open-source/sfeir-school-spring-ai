package com.sfeir.calendar.persisence;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sfeir.calendar.domain.model.CalendarRecord;
import com.sfeir.calendar.listrecords.RetrieveRecords;
import com.sfeir.calendar.persisence.entity.CalendarItemJpaEntity;

@Service
public class CalendarRecordRetriever implements RetrieveRecords {

    private final CalendarItemRepository repository;

    public CalendarRecordRetriever(CalendarItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CalendarRecord> getAll() {
        return repository.findAll().stream()
                .map(this::mapToCalendarRecord)
                .toList();
    }

    private CalendarRecord mapToCalendarRecord(CalendarItemJpaEntity entity) {
        return new CalendarRecord(
                entity.getDetail().getDay(),
                entity.getDetail().getFrom(),
                entity.getDetail().getTo(),
                entity.getDetail().getDescription()
        );
    }
}
