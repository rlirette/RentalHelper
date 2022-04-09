package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator.EmailFormatBody;
import com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator.EmailFormatParams;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.model.dao.mail.SourceMail;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EmailFormator {

    private final EmailFormatParams params;
    private final EmailFormatBody emailFormatBody;

    public MailStructure formatMonthly(Set<Event> events, SourceMail sourceMail){
        SourceMailContentTemplate rectificationTemplate = sourceMail.getSourceMailContentTemplate().stream()
                .findFirst()
                .orElseThrow();
        MailStructure mailStructure = format(sourceMail, rectificationTemplate);
        mailStructure.setBody(emailFormatBody.formatBody(params.getBody(rectificationTemplate), events, rectificationTemplate.getItem()));
        return mailStructure;
    }

    public MailStructure formatDaily(Set<Event> eventsUnchanged, Set<Event> eventsToCreate, Set<Event> eventsToDelete, SourceMail sourceMail){
        SourceMailContentTemplate rectificationTemplate = sourceMail.getSourceMailContentTemplate().stream()
                .findFirst()
                .orElseThrow();
        MailStructure mailStructure = format(sourceMail, rectificationTemplate);

        final Set<Event> events = concatWithStatus(eventsUnchanged, eventsToCreate, eventsToDelete);
        mailStructure.setBody(emailFormatBody.formatBody(params.getBody(rectificationTemplate), events, rectificationTemplate.getItem()));
        return mailStructure;
    }

    public MailStructure format(SourceMail sourceMail, SourceMailContentTemplate rectificationTemplate){
        MailStructure mailStructure = new MailStructure();
        mailStructure.setTransmitter(params.getTransmitter(sourceMail));
        mailStructure.setRecipient(params.getRecipients(sourceMail.getRecipients()));
        mailStructure.setCopy(params.getCopy(sourceMail.getMailCopies()));
        mailStructure.setTitle(params.getTitle(rectificationTemplate));
        return mailStructure;
    }

    private Set<Event> concatWithStatus(Set<Event> eventsUnchanged, Set<Event> eventsToCreate, Set<Event> eventsToDelete){
        eventsToCreate.forEach(e -> e.setIsToDelete(false));
        eventsToDelete.forEach(e -> e.setIsToDelete(true));

        Set<Event> eventsConcat = new HashSet<>(eventsUnchanged);
        eventsConcat.addAll(eventsToCreate);
        eventsConcat.addAll(eventsToDelete);
        return eventsConcat;
    }
}
