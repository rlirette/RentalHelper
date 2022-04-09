package com.rlirette.tools.rentalhelper.api.service.impl;

import biweekly.component.VEvent;
import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventConverter {

    private static final ZoneId defaultZoneId = ZoneId.systemDefault();
    public static final String DEFAULT_SUMMARY = "Reserved";

    public List<VEvent> convert(List<ApiEvent> apiEvents){
            return apiEvents.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private VEvent convert(ApiEvent apiEvent){
        VEvent vEvent = new VEvent();
        vEvent.setDescription(apiEvent.getCode());
        vEvent.setDateStart(convert(apiEvent.getStartDate()));
        vEvent.setDateEnd(convert(apiEvent.getEndDate()));
        vEvent.setSummary(DEFAULT_SUMMARY);
        return vEvent;
    }

    private Date convert(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }
}
