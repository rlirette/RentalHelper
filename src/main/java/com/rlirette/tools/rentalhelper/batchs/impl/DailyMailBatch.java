package com.rlirette.tools.rentalhelper.batchs.impl;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.*;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.model.dao.EventStatus;
import com.rlirette.tools.rentalhelper.repository.crud.EventCrud;
import com.rlirette.tools.rentalhelper.repository.crud.SourceEventCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyMailBatch {
    private final EventsFilter eventsFilter;
    private final CalendarParser calendarParser;
    private final EventCrud eventCrud;
    private final SourceEventCrud sourceEventCrud;
    private final EmailFormator eMailFormator;
    private final EmailSender mailSender;
    private final DateDefiner dateDefiner;

    public void sendMailOfTheDay(boolean isMailInclude){
        sourceEventCrud.findAll().withTemplateName("update").forEach(source -> {
            final Set<String> icsUris = sourceEventCrud.deductIcsCalendarUriFrom(source.getSourcesIcs());
            final Set<Event> eventsIcs = calendarParser.findAllEventsFrom(icsUris);
            final Set<Event> eventsIcsOfDateInterval = eventsFilter.keep(eventsIcs).after(dateDefiner.start()).before(dateDefiner.end());
            final Set<Event> eventsDbDateInterval = eventCrud.findAllAfter(dateDefiner.start()).andBefore(dateDefiner.end());
            final Set<Event> eventsToCreate = deductToCreate(eventsIcsOfDateInterval, eventsDbDateInterval);
            log.info("\n------------{} events to create", eventsToCreate.size());
            final Set<Event> eventsToDelete = deductToDelete(eventsDbDateInterval, eventsIcsOfDateInterval);
            log.info("\n------------{} events to delete", eventsToDelete.size());
            final Set<Event> eventsToModify = deductInBothButDifferentDates(eventsDbDateInterval, eventsIcsOfDateInterval);
            log.info("\n------------{} events to modify", eventsToModify.size());

            if(eventsToCreate.isEmpty() && eventsToDelete.isEmpty() && eventsToModify.isEmpty())
                return;

            final Set<Event> eventsUnChanged = deductNotChangedEvents(eventsDbDateInterval, eventsToDelete, eventsToModify);

            final Set<Event> events = concat(eventsUnChanged, eventsToCreate, eventsToDelete, eventsToModify);

            if(isMailInclude) {
                final MailStructure mailStructure = eMailFormator.format(events, source.getSourceMail());
                mailSender.send(mailStructure, source);
            }
            final Set<Event> eventsUpdated = deductEventsToUpdate(eventsUnChanged, eventsToCreate, eventsToModify);
            source.setEvents(eventsUpdated);
            sourceEventCrud.update(source);
        });
    }

    private Set<Event> deductToCreate(Set<Event> firsts, Set<Event> seconds) {
        return deductInFirstButNotInSecondArg(firsts, seconds)
                .map(evDb -> {
                    evDb.setStatus(EventStatus.TO_CREATE);
                    return evDb;
                })
                .collect(Collectors.toSet());
    }

    private Set<Event> deductToDelete(Set<Event> firsts, Set<Event> seconds) {
        return deductInFirstButNotInSecondArg(firsts, seconds)
                .map(evDb -> {
                    evDb.setStatus(EventStatus.TO_DELETE);
                    return evDb;
                })
                .collect(Collectors.toSet());
    }

    private Stream<Event> deductInFirstButNotInSecondArg(Set<Event> firsts, Set<Event> seconds) {
        return firsts.stream()
                .filter(first -> seconds.stream().noneMatch(second -> second.getCode().equals(first.getCode())));
    }

    private Set<Event> deductInBothButDifferentDates(Set<Event> dbEvents, Set<Event> icsEvents) {
        return dbEvents.stream()
                .filter(evDb ->
                    icsEvents.stream()
                            .anyMatch(evIcs -> evIcs.getCode().equals(evDb.getCode()) && (!evIcs.getStartDate().equals(evDb.getStartDate()) || !evIcs.getEndDate().equals(evDb.getEndDate())))
                )
                .map(evDb -> {
                    final Optional<Event> icsEventCorrespondingToDbEvent = icsEvents.stream()
                            .filter(evIcs -> evIcs.getCode().equals(evDb.getCode()) && (!evIcs.getStartDate().equals(evDb.getStartDate()) || !evIcs.getEndDate().equals(evDb.getEndDate())))
                            .findFirst();
                    icsEventCorrespondingToDbEvent.ifPresent(evIcs -> {
                        evDb.setStartDate(evIcs.getStartDate());
                        evDb.setEndDate(evIcs.getEndDate());
                    });
                    return evDb;
                })
                .map(evDb -> {
                    evDb.setStatus(EventStatus.TO_MODIFY);
                    return evDb;
                }).collect(Collectors.toSet());
    }

    private Set<Event> deductNotChangedEvents(Set<Event> eventsFromDbDateInterval, Set<Event> eventsToDelete, Set<Event> eventsToModify) {
        final Set<Event> eventsThatNotChanged = new HashSet<>(eventsFromDbDateInterval);
        eventsThatNotChanged.removeAll(eventsToDelete);
        eventsThatNotChanged.removeAll(eventsToModify);
        return eventsThatNotChanged.stream()
                .map(evDb -> {
                    evDb.setStatus(EventStatus.UNCHANGED);
                    return evDb;
                }).collect(Collectors.toSet());
    }

    private Set<Event> concat(Set<Event>... eventsType){
        return Arrays.stream(eventsType).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private Set<Event> deductEventsToUpdate(Set<Event> eventsUnChanged, Set<Event> eventsToCreate, Set<Event> eventsToModify) {
        final Set<Event> eventsToUpdate = new HashSet<>(eventsUnChanged);
        eventsToUpdate.addAll(eventsToCreate);
        eventsToUpdate.addAll(eventsToModify);
        return eventsToUpdate;
    }
}
