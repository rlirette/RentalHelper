package com.rlirette.tools.rentalhelper.api.service.impl;

import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.io.text.ICalWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@Slf4j
@Service
public class FileIcsWriter {

    public static final String DEFAULT_FILE_NAME = "RentalHelper.ics";

    public File write(ICalendar ical){
        File outputFile = new File(DEFAULT_FILE_NAME);
        try (StringWriter stringWriter = new StringWriter();
             ICalWriter icalWriter = new ICalWriter(stringWriter, ICalVersion.V2_0);
             BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(outputFile))) {

            icalWriter.write(ical);
            bufferedWriter.write(stringWriter.toString());
        } catch (IOException e) {
            log.info("Unable to create ICS file", e.getMessage());
        } finally {
            return outputFile;
        }
    }
}
