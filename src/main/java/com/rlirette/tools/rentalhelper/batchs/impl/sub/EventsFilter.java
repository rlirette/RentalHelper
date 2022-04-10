package com.rlirette.tools.rentalhelper.batchs.impl.sub;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rlirette.tools.rentalhelper.tools.Common.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventsFilter {
    public StartDateSelector keep(Set<Event> events){
        return new StartDateSelector(events);
    }

    public class StartDateSelector {

        private Set<Event> events;

        public StartDateSelector(Set<Event> events) {
            this.events = events;
        }

        public EndDateSelector after(LocalDate startDate){
            return new EndDateSelector(events, startDate);
        }

        public class EndDateSelector {

            private Set<Event> events;
            private LocalDate startDate;

            public EndDateSelector(Set<Event> events, LocalDate startDate) {
                this.events = events;
                this.startDate = startDate;
            }

            public Set<Event> before(LocalDate endDate){
                final Set<Event> eventsOfInterval = events.stream()
                        .filter(e -> e.getStartDate().isAfter(startDate))
                        .filter(e -> e.getStartDate().isBefore(endDate))
                        .collect(Collectors.toSet());
                log.info("\n{} ICS events found between {} and {}", eventsOfInterval.size(), format(startDate), format(endDate));
                return eventsOfInterval;
            }
        }
    }
}
