package com.rlirette.tools.rentalhelper.api.service;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import com.rlirette.tools.rentalhelper.api.repository.ApiEventRepository;
import com.rlirette.tools.rentalhelper.api.service.impl.EventConverter;
import com.rlirette.tools.rentalhelper.api.service.impl.FileIcsWriter;
import com.rlirette.tools.rentalhelper.api.service.impl.ICalendarBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnIcsEventsService {
    private final ApiEventRepository apiEventRepository;
    private final EventConverter eventConverter;
    private final ICalendarBuilder iCalendarBuilder;
    private final FileIcsWriter fileIcsWriter;

    public ByteArrayResource findAll() throws IOException {
        final List<ApiEvent> allEvents = apiEventRepository.findAll();
        final List<VEvent> vEvents = eventConverter.convert(allEvents);
        ICalendar ical = iCalendarBuilder.buildWith(vEvents);
        final File icsFile = fileIcsWriter.write(ical);
        return prepareForResponse(icsFile);
    }

    public void save(@RequestBody final List<ApiEvent> apiEvents) {
        apiEventRepository.saveAll(apiEvents);
    }

    private ByteArrayResource prepareForResponse(File icsFile) throws IOException {
        Path path = Paths.get(icsFile.getAbsolutePath());
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
