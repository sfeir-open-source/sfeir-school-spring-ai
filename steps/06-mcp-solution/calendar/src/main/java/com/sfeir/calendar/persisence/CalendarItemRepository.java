package com.sfeir.calendar.persisence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfeir.calendar.persisence.entity.CalendarItemJpaEntity;

@Repository
public interface CalendarItemRepository extends JpaRepository<CalendarItemJpaEntity, UUID> {
}
