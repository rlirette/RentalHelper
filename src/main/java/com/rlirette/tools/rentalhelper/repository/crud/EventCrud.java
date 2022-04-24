package com.rlirette.tools.rentalhelper.repository.crud;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

import static com.rlirette.tools.rentalhelper.tools.Common.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventCrud {
    private static final ExampleMatcher MODEL_MATCHER_WITHOUT_ID = ExampleMatcher.matching().withIgnorePaths("id");

    private final EventRepository eventRepository;

    public UpdateEndDateSelector findAllAfter(LocalDate startDate){
        return new UpdateEndDateSelector(startDate);
    }

    public class UpdateEndDateSelector {

        private LocalDate startDate;

        public UpdateEndDateSelector(LocalDate startDate) {
            this.startDate = startDate;
        }

        public Set<Event> andBefore(LocalDate endDate){
            final Set<Event> eventsInInterval = eventRepository.findBetween(startDate, endDate);
            log.info("\n------------{} DB events found between {} and {}", eventsInInterval.size(), format(startDate), format(endDate));
            return eventsInInterval;
        }
    }
}
