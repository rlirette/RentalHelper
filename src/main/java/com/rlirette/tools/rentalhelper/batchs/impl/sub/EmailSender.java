package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import com.rlirette.tools.rentalhelper.batchs.impl.sub.mailsender.EmailServerConnectionConfiguration;
import com.rlirette.tools.rentalhelper.model.MailStructure;
import com.rlirette.tools.rentalhelper.model.dao.Source;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.rlirette.tools.rentalhelper.tools.Common.popup;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final EmailServerConnectionConfiguration emailServerConnectionConfiguration;

    public void send(MailStructure mailStructure, Source source){
        try {
            final JavaMailSenderImpl javaMailSender = init(emailServerConnectionConfiguration.initSession(source));
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            getMimeMessage(mimeMessage, mailStructure);
            javaMailSender.send(mimeMessage);
            log.info("MonthMailBatch - mail successfully send to {}", Arrays.stream(mailStructure.getRecipient()).collect(Collectors.joining(", ")));
        }catch (Exception e){
            popup("Can't send mail cause : " + e.getMessage());
        }
    }

    private JavaMailSenderImpl init(Session session) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setSession(session);
        return javaMailSender;
    }

    private MimeMessageHelper getMimeMessage(MimeMessage mimeMessage, MailStructure mailStructure) throws MessagingException {
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom(mailStructure.getTransmitter());
        mimeMessageHelper.setTo(mailStructure.getRecipient());
        mimeMessageHelper.setSubject(mailStructure.getTitle());
        mimeMessageHelper.setText(mailStructure.getBody(), true);
        return mimeMessageHelper;
    }
}
