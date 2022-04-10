package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateDefiner {

    private static final LocalDate TO_MORROW = LocalDate.now().plusDays(1);
    private static final LocalDate LAST_DAY_OF_THE_MONTH = LocalDate.now().withDayOfMonth(TO_MORROW.lengthOfMonth());
    private static final LocalDate THE_15_OF_THE_NEXT_MONTH = LocalDate.now().plusMonths(1).withDayOfMonth(15);

    public LocalDate start(){
        return TO_MORROW;
    }

    public LocalDate end(){
        if(start().getDayOfMonth() >= 15)
            return THE_15_OF_THE_NEXT_MONTH;
        return LAST_DAY_OF_THE_MONTH;
    }
}
