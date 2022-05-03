package com.rlirette.tools.rentalhelper.repository.crud;

import com.rlirette.tools.rentalhelper.api.repository.ApiEventRepository;
import com.rlirette.tools.rentalhelper.model.EventMapper;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import com.rlirette.tools.rentalhelper.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.rlirette.tools.rentalhelper.tools.Common.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventCrud {
    private final EventRepository eventRepository;
    private final ApiEventRepository apiEventRepository;
    private final EventMapper eventMapper;

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
            final Set<Event> apiEventsInInterval = eventMapper.mapToEvent(apiEventRepository.findBetween(startDate, endDate));
            final Set<Event> allEvents = new HashSet<>(eventsInInterval);
            allEvents.addAll(apiEventsInInterval);
            log.info("\n------------{} DB events found between {} and {}", allEvents.size(), format(startDate), format(endDate));
            return allEvents;
        }
    }
}
