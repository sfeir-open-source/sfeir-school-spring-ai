package com.sfeir.calendar.persisence;

import java.util.Map;
import static java.util.Map.entry;

import org.springframework.stereotype.Service;

import com.sfeir.calendar.createcalendarrecord.CreateRecord;
import com.sfeir.calendar.domain.model.CalendarRecord;
import com.sfeir.calendar.persisence.entity.CalendarItemJpaEntity;
import com.sfeir.calendar.persisence.repository.CalendarItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarRecordCreator implements CreateRecord {

    private final CalendarItemRepository repository;

    @Override
    public void create(CalendarRecord calendarRecord) {
        CalendarItemJpaEntity entity = new CalendarItemJpaEntity();
        entity.getDetail().putAll(Map.ofEntries(
                entry("day", calendarRecord.day().toString()),
                entry("from", calendarRecord.from().toString()),
                entry("to", calendarRecord.to().toString()),
                entry("description", calendarRecord.description())
        ));
        
        repository.save(entity);
    }
}
