package com.rlirette.tools.rentalhelper.batchs.impl.sub.mailsender;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EmailConnectionParams {

    @Value("${mail.smtp.auth}")
    private boolean isAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String isTlsEnable;

    @Value("${mail.username}")
    private String userName;

    @Value("${mail.password}")
    private String password;
}
