package com.rlirette.tools.rentalhelper.api.repository;

import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import com.rlirette.tools.rentalhelper.model.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface ApiEventRepository extends JpaRepository<ApiEvent, Integer> {

    @Query("SELECT e FROM Event e WHERE e.startDate BETWEEN :startDate AND :endDate")
    Set<Event> findBetween(LocalDate startDate, LocalDate endDate);
}
