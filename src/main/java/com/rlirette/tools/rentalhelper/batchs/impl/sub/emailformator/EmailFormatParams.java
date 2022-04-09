package com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator;

import com.rlirette.tools.rentalhelper.model.dao.mail.SourceMail;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentHeaderCopy;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentHeaderRecipient;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmailFormatParams {

    public static final String BALISE_NEXT_MONTH = "<next_month/>";

    public String getTransmitter(SourceMail sourceMail){
        return sourceMail.getTransmitter().trim().toLowerCase();
    }

    public String[] getRecipients(Set<SourceMailContentHeaderRecipient> recipients) {
        final Set<String> recipientsValue = recipients.stream().map(SourceMailContentHeaderRecipient::getRecipient).collect(Collectors.toSet());
        return formatRecipents(recipientsValue);
    }

    public String[] getCopy(Set<SourceMailContentHeaderCopy> mailCopies) {
        final Set<String> mailCopiesValue = mailCopies.stream().map(SourceMailContentHeaderCopy::getCopy).collect(Collectors.toSet());
        return formatRecipents(mailCopiesValue);
    }

    public String getTitle(SourceMailContentTemplate sourceMailContentTemplate){
        return sourceMailContentTemplate.getTitle().replace(BALISE_NEXT_MONTH, geNextMonthInFrenchFullFormat());
    }

    private String geNextMonthInFrenchFullFormat() {
        final Month nextMonth = LocalDate.now().plusMonths(1).getMonth();
        return nextMonth.getDisplayName(TextStyle.FULL, Locale.FRANCE);
    }

    private String[] formatRecipents(Set<String> recipients){
        return recipients.toArray(String[]::new);
    }

    public String getBody(SourceMailContentTemplate sourceMailContentTemplate) {
        return sourceMailContentTemplate.getBody();
    }
}
