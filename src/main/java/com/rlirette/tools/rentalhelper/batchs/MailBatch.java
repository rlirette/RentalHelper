package com.rlirette.tools.rentalhelper.batchs;

import com.rlirette.tools.rentalhelper.batchs.impl.DailyMailBatch;
import com.rlirette.tools.rentalhelper.batchs.impl.MonthlyMailBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MailBatch {
    private final MonthlyMailBatch monthlyMailBatch;
    private final DailyMailBatch dailyMailBatch;

    @Scheduled(cron = "00 00 12 15 * ?", zone = "Europe/Paris")
    public void sendNextMonthReservationsMail() {
        log.info("\n------------ monthly batch ------------\n");
        monthlyMailBatch.sendMailOfTheMonth();
    }

    @Scheduled(cron = "00 00 12 * * ?", zone = "Europe/Paris")
    public void sendRectificationMail() {
        log.info("\n------------ daily batch ------------\n");
        dailyMailBatch.sendMailOfTheDay();
    }
}
