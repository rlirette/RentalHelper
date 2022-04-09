package com.rlirette.tools.rentalhelper.batchs.impl.sub.emailformator;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class EmailFormatBody {

    public static final String BALISE_EVENTS = "<events/>";
    private final EmailFormatDate formatDate;

    public String formatBody(String body, Set<Event> events, String item){
        return body.replace(BALISE_EVENTS, formatEvents(events, item));
    }

    private String formatEvents(Set<Event> events, String itemTemplate){
        StringBuilder eventsFormatted = new StringBuilder();
        deductListSortedByStartDate(events).forEach(event ->
            eventsFormatted.append(getEventLineFormatted(event, itemTemplate))
        );
        return eventsFormatted.toString();
    }

    private String getEventLineFormatted(Event event, String itemTemplate){
        return itemTemplate
                .replace("<startDate/>", formatDate.dateFormat(event.getStartDate()))
                .replace("<endDate/>", formatDate.dateFormat(event.getEndDate()))
                .replace("<keyBoxCode/>", event.getCode())
                .replace("<status/>", deductStatusFrom(event.getIsToDelete()));
    }

    private String deductStatusFrom(Boolean isToDelete){
        if(isToDelete == null)
            return "";
        return isToDelete ? "Annulé" : "Créé";
    }

    private List<Event> deductListSortedByStartDate(Set<Event> events){
        List<Event> eventsList = new ArrayList<>(events);
        eventsList.sort(Comparator.comparing(Event::getStartDate));
        return eventsList;
    }
}
