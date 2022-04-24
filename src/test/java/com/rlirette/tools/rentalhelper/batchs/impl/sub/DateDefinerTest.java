package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DateDefinerTest {

    private DateDefiner dateDefiner;
    private LocalDate currentDate;

    @BeforeEach
    void setup(){
        dateDefiner = new DateDefiner();
        currentDate = LocalDate.now();
    }

    @Test
    void if_before_15_then_end_date_is_last_day_of_month(){
        final LocalDate end = dateDefiner.end();

        if(currentDate.getDayOfMonth() < 15) {
            assertThat(end.getMonth()).isEqualTo(currentDate.getMonth());
            assertThat(end.getDayOfMonth() > 25).isTrue();
            assertThat(end.getDayOfMonth()).isEqualTo(currentDate.lengthOfMonth());
        }else if(currentDate.getDayOfMonth() >= 15) {
            assertThat(end.getMonth()).isEqualTo(currentDate.getMonth().plus(1));
            assertThat(end.getDayOfMonth()).isEqualTo(15);
        }
    }
}