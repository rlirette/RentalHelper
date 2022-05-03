package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator.EmailFormatBody;
import com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator.EmailFormatParams;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.model.dao.mail.SourceMail;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class EmailFormator {

    private final EmailFormatParams params;
    private final EmailFormatBody emailFormatBody;

    public MailStructure format(Set<Event> events, SourceMail sourceMail){
        SourceMailContentTemplate rectificationTemplate = sourceMail.getSourceMailContentTemplate().stream()
                .findFirst()
                .orElseThrow();
        MailStructure mailStructure = build(sourceMail, rectificationTemplate);
        mailStructure.setBody(emailFormatBody.formatBody(params.getBody(rectificationTemplate), events, rectificationTemplate.getItem()));
        return mailStructure;
    }

    public MailStructure build(SourceMail sourceMail, SourceMailContentTemplate rectificationTemplate){
        MailStructure mailStructure = new MailStructure();
        mailStructure.setTransmitter(params.getTransmitter(sourceMail));
        mailStructure.setRecipient(params.getRecipients(sourceMail.getRecipients()));
        mailStructure.setCopy(params.getCopy(sourceMail.getMailCopies()));
        mailStructure.setTitle(params.getTitle(rectificationTemplate));
        return mailStructure;
    }
}
