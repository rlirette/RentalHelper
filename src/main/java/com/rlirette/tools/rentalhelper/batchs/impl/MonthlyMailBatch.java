package com.rlirette.tools.rentalhelper.batchs.impl;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.CalendarParser;
import com.rlirette.tools.rentalhelper.batchs.impl.sub.EmailFormator;
import com.rlirette.tools.rentalhelper.batchs.impl.sub.EmailSender;
import com.rlirette.tools.rentalhelper.batchs.impl.sub.EventsFilter;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.repository.crud.SourceEventCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MonthlyMailBatch {
    private static final LocalDate CURRENT_DATE = LocalDate.now();
    private static final LocalDate LAST_DAY_OF_CURRENT_MONTH = CURRENT_DATE.withDayOfMonth(CURRENT_DATE.lengthOfMonth());
    private static final LocalDate FIRST_DAY_OF_THE_MONTH__IN_TWO_MONTHS = LAST_DAY_OF_CURRENT_MONTH.plusMonths(2).withDayOfMonth(1);

    private final CalendarParser calendarParser;
    private final EventsFilter eventsFilter;
    private final EmailFormator eMailFormator;
    private final SourceEventCrud sourceEventCrud;
    private final EmailSender mailSender;

    public void sendMailOfTheMonth(){
        sourceEventCrud.findAll().withTemplateName("create").forEach(source -> {
            final Set<String> icsUris = sourceEventCrud.deductIcsCalendarUriFrom(source.getSourcesIcs());
            final Set<Event> eventsIcs = calendarParser.findAllEventsFrom(icsUris);
            final Set<Event> eventsIcsOfDateInterval = eventsFilter.keep(eventsIcs).after(LAST_DAY_OF_CURRENT_MONTH).before(FIRST_DAY_OF_THE_MONTH__IN_TWO_MONTHS);
            final MailStructure mailStructure = eMailFormator.format(eventsIcsOfDateInterval, source.getSourceMail());
            mailSender.send(mailStructure, source);
        });
    }
}
