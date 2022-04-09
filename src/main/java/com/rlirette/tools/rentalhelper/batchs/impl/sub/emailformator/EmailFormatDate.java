package com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmailFormatDate {

    public String dateFormat(LocalDate date){
        return intFormat(date.getDayOfMonth()) + "/" + intFormat(date.getMonth().getValue());
    }

    private String intFormat(int toFormat){
        return toFormat < 10 ? "0" + toFormat : String.valueOf(toFormat);
    }
}
