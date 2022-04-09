package com.rlirette.tools.rentalhelper;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class Pigeon {

    private EasyRandom randomGenerator;

    @BeforeEach
    void setup(){
        randomGenerator = new EasyRandom();
    }

    @Disabled("KO")
    @Test
    void p(){
        Set<Event> icsEvents = randomGenerator.objects(Event.class, 2).collect(Collectors.toSet());
        Set<Event> dbEvents = randomGenerator.objects(Event.class, 2).collect(Collectors.toSet());
//        icsEvents.add(dbEvents.get(0));
//        dbEvents.add(icsEvents.get(0));


        final Set<Event> eventsToCreate = icsEvents.stream()
                .filter(ics -> dbEvents.stream().noneMatch(db -> db.getStartDate().equals(ics.getStartDate())))
                .collect(Collectors.toSet());

        final Set<Event> eventsToDelete = dbEvents.stream()
                .filter(db -> icsEvents.stream().noneMatch(ics -> ics.getStartDate().equals(db.getStartDate())))
                .collect(Collectors.toSet());

        assertThat(eventsToCreate).hasSize(1);
//        assertThat(eventsToCreate).contains(icsEvents.get(0));

        assertThat(eventsToDelete).hasSize(2);
//        assertThat(eventsToDelete).contains(dbEvents.get(0));
    }
}
