package com.sfeir.calendar.domain.model;


import java.time.LocalDate;
import java.time.LocalTime;

public record CalendarRecord(LocalDate day, LocalTime from, LocalTime to, String description) {}
