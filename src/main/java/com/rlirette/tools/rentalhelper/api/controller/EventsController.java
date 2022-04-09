package com.rlirette.tools.rentalhelper.api.controller;

import com.rlirette.tools.rentalhelper.repository.crud.SourceEventCrud;
import com.rlirette.tools.rentalhelper.api.service.mock.MockNewEventCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventsController {
    private final SourceEventCrud sourceEventCrud;

    @GetMapping("/init")
    public ResponseEntity<Void> init() throws IOException {
        sourceEventCrud.initNew(MockNewEventCrud.buildNew());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
