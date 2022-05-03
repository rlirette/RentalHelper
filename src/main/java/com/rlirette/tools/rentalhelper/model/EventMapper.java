package com.rlirette.tools.rentalhelper.model;

import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(config = MapperConfiguration.class)
public interface EventMapper {

    Set<Event> mapToEvent(Set<ApiEvent> source);
}
