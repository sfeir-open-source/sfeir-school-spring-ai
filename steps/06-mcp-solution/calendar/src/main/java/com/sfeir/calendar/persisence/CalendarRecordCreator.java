package com.sfeir.calendar.persisence;

import org.springframework.stereotype.Service;

import com.sfeir.calendar.createcalendarrecord.CreateRecord;
import com.sfeir.calendar.domain.model.CalendarRecord;
import com.sfeir.calendar.persisence.entity.CalendarItemJpaEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarRecordCreator implements CreateRecord {

    private final CalendarItemRepository repository;

    @Override
    public void create(CalendarRecord calendarRecord) {
        CalendarItemJpaEntity entity = new CalendarItemJpaEntity();
        entity.getDetail().setDay(calendarRecord.day());
        entity.getDetail().setFrom(calendarRecord.from());
        entity.getDetail().setTo(calendarRecord.to());
        entity.getDetail().setDescription(calendarRecord.description());
        repository.save(entity);
    }
}
