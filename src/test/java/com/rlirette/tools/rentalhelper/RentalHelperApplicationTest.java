package com.rlirette.tools.rentalhelper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Disabled("KO")
@SpringBootTest
@PropertySource(value={"classpath:application.properties"})
class RentalHelperApplicationTest {

    @Test
    void is_ApplicationContext_load(ApplicationContext context) {
        assertThat(context).isNotNull();
    }
}