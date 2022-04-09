package com.rlirette.tools.rentalhelper.api.service.impl;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICalendarBuilder {

    public static final String DEFAULT_PRODUCT_ID = "RentalHelper";

    public ICalendar buildWith(List<VEvent> vEvents){
        ICalendar ical = new ICalendar();
        ical.setProductId(DEFAULT_PRODUCT_ID);

        vEvents.forEach(ical::addEvent);

        Biweekly.write(ical).go();

        return ical;
    }
}
