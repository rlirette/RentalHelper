package com.rlirette.tools.rentalhelper.repository;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Disabled("KO")
@SpringBootTest
//@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void query_is_ok(){
        final Set<Event> between = eventRepository.findBetween(LocalDate.now(), LocalDate.now());
        assertThat(between).isNotNull();
    }


}