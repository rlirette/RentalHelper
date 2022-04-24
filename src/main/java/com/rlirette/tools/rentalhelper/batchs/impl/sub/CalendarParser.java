package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateOrDateTimeProperty;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalendarParser {

    public static final int LAST_4_CHARS__CORRESPONDING_TO_LAST_4_DIGIT_OF_CLIENT_PHONE_NUMBER = 4;

    @Value("${airbnb.ics.ignore.events.exact.title}")
    private String ignoreEvents;

    private final UrlContentGetter urlContentGetter;

    public Set<Event> findAllEventsFrom(Set<String> icsUris) {
        final Set<Event> events = icsUris.stream()
                .map(urlContentGetter::getContentFrom)
                .map(this::parseToVEvents)
                .map(this::filterInvalid)
                .map(this::parseToEvents)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        log.info("\n------------{} ICS events found", events.size());
        return events;
    }

    public Set<VEvent> parseToVEvents(String icsContent) {
        ICalendar icalendar = convert(icsContent);
        return getVEventsOf(icalendar);
    }

    public Set<VEvent> filterInvalid(Set<VEvent> vEvents) {
        return filter(vEvents, ignoreEvents);
    }

    public Set<Event> parseToEvents(Set<VEvent> vEvents) {
        return vEvents.stream()
                .map(vEvent ->
                        Event.builder()
                                .startDate(convert(vEvent.getDateStart()))
                                .endDate(convert(vEvent.getDateEnd()))
                                .code(retrieveCodeFrom(vEvent.getDescription().getValue()))
                                .build()
                )
                .collect(Collectors.toSet());
    }

    private ICalendar convert(String icsContent) {
        return Biweekly.parse(icsContent).first();
    }

    private Set<VEvent> getVEventsOf(ICalendar icalendar) {
        return new HashSet<>(icalendar.getEvents());
    }

    private Set<VEvent> filter(Set<VEvent> vEvents, String ignoreEvents) {
        return vEvents.stream()
                .filter(vEvent -> !vEvent.getSummary().getValue().equals(ignoreEvents))
                .collect(Collectors.toSet());
    }

    private LocalDate convert(DateOrDateTimeProperty dateToConvert) {
        return LocalDate.ofInstant(Instant.ofEpochMilli(dateToConvert.getValue().getTime()), ZoneId.systemDefault());
    }

    private String retrieveCodeFrom(String description){
        return description.substring(description.length() - LAST_4_CHARS__CORRESPONDING_TO_LAST_4_DIGIT_OF_CLIENT_PHONE_NUMBER);
    }
}
