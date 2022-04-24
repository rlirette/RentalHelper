package com.rlirette.tools.rentalhelper.api.controller;

import com.rlirette.tools.rentalhelper.api.service.mock.MockNewEventCrud;
import com.rlirette.tools.rentalhelper.batchs.impl.DailyMailBatch;
import com.rlirette.tools.rentalhelper.batchs.impl.MonthlyMailBatch;
import com.rlirette.tools.rentalhelper.repository.crud.SourceEventCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventsController {
    private final SourceEventCrud sourceEventCrud;
    private final MonthlyMailBatch monthlyMailBatch;
    private final DailyMailBatch dailyMailBatch;

    @GetMapping("/init")
    public ResponseEntity<Void> init() throws IOException {
        log.info("\n\n------------ request init ({}) ------------\n", LocalDateTime.now());
        sourceEventCrud.initNew(MockNewEventCrud.buildNew());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/daily")
    public ResponseEntity<Void> updateDailyWithoutMail() throws IOException {
        log.info("\n\n------------ request daily without mail ({}) ------------\n", LocalDateTime.now());
        dailyMailBatch.sendMailOfTheDay(false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/daily/mail")
    public ResponseEntity<Void> updateDailyWithMail() throws IOException {
        log.info("\n\n------------ request daily mail ({}) ------------\n", LocalDateTime.now());
        dailyMailBatch.sendMailOfTheDay(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/monthly/mail")
    public ResponseEntity<Void> updateMonthlyWithMail() throws IOException {
        log.info("\n\n------------ request monthly mail ({}) ------------\n", LocalDateTime.now());
        monthlyMailBatch.sendMailOfTheMonth();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
