package com.rlirette.tools.rentalhelper.api.repository;

import com.rlirette.tools.rentalhelper.api.model.ApiEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface ApiEventRepository extends JpaRepository<ApiEvent, Integer> {

    @Query("SELECT e FROM ApiEvent e WHERE e.startDate BETWEEN :startDate AND :endDate")
    Set<ApiEvent> findBetween(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);
}
