package com.rlirette.tools.rentalhelper.repository;

import com.rlirette.tools.rentalhelper.model.dao.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.startDate BETWEEN :startDate AND :endDate")
    Set<Event> findBetween(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
