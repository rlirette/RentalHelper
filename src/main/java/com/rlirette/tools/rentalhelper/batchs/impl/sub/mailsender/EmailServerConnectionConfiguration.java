package com.rlirette.tools.rentalhelper.batchs.impl.sub.mailsender;

import com.rlirette.tools.rentalhelper.model.dao.Source;
import com.rlirette.tools.rentalhelper.model.dao.mail.config.SourceMailConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailServerConnectionConfiguration {

    private final EmailConnectionParams params;

    public Session initSession(Source source){
        final Properties properties = init(source.getSourceMail().getSourceMailConfig());
        return init(properties);
    }

    private Properties init(SourceMailConfig sourceMailConfig) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", params.isAuth());
        prop.put("mail.smtp.starttls.enable", params.getIsTlsEnable());
        prop.put("mail.smtp.host", sourceMailConfig.getHost());
        prop.put("mail.smtp.port", sourceMailConfig.getPort());
        return prop;
    }

    private Session init(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(params.getUserName(), params.getPassword());
            }
        });
    }
}
