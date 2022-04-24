package com.rlirette.tools.rentalhelper.batchs.impl;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.*;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.repository.crud.EventCrud;
import com.rlirette.tools.rentalhelper.repository.crud.SourceEventCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
            final Set<Event> eventsToCreate = deductInFirstButNotInSecondArg(eventsIcsOfDateInterval, eventsDbDateInterval);
            log.info("\n------------{} events to create", eventsToCreate.size());
            final Set<Event> eventsToDelete = deductInFirstButNotInSecondArg(eventsDbDateInterval, eventsIcsOfDateInterval);
            log.info("\n------------{} events to delete\n\n", eventsToDelete.size());

            if(eventsToCreate.isEmpty() && eventsToDelete.isEmpty())
                return;

            final Set<Event> eventsUnChanged = deductNotChangedEvents(eventsDbDateInterval, eventsToDelete);
            if(isMailInclude) {
                final MailStructure mailStructure = eMailFormator.formatDaily(eventsUnChanged, eventsToCreate, eventsToDelete, source.getSourceMail());
                mailSender.send(mailStructure, source);
            }
            final Set<Event> eventsUpdated = deductEventsToUpdate(eventsUnChanged, eventsToCreate);
            source.setEvents(eventsUpdated);
            sourceEventCrud.update(source);
        });
    }

    private Set<Event> deductInFirstButNotInSecondArg(Set<Event> firsts, Set<Event> seconds) {
        return firsts.stream()
                .filter(first -> seconds.stream().noneMatch(second -> second.getStartDate().equals(first.getStartDate())))
                .filter(first -> seconds.stream().noneMatch(second -> second.getCode().equals(first.getCode())))
                .collect(Collectors.toSet());
    }

    private Set<Event> deductNotChangedEvents(Set<Event> eventsFromDbDateInterval, Set<Event> eventsToDelete) {
        final Set<Event> eventsThatNotChanged = new HashSet<>(eventsFromDbDateInterval);
        eventsThatNotChanged.removeAll(eventsToDelete);
        return eventsThatNotChanged;
    }

    private Set<Event> deductEventsToUpdate(Set<Event> eventsUnChanged, Set<Event> eventsToCreate) {
        final Set<Event> eventsToUpdate = new HashSet<>(eventsUnChanged);
        eventsToUpdate.addAll(eventsToCreate);
        return eventsToUpdate;
    }
}
