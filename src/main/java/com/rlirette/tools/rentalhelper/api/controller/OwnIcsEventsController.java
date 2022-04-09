package com.rlirette.tools.rentalhelper.api.controller;

import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import com.rlirette.tools.rentalhelper.api.service.OwnIcsEventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ics")
@RequiredArgsConstructor
public class OwnIcsEventsController {
    private final OwnIcsEventsService ownIcsEventsService;

    @GetMapping
    public ResponseEntity<Resource> findAll() throws IOException {
        final ByteArrayResource icsFile = ownIcsEventsService.findAll();
        return new ResponseEntity<>(icsFile, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody final List<ApiEvent> apiEvents) {
        ownIcsEventsService.save(apiEvents);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
