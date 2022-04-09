package com.rlirette.tools.rentalhelper.repository;

import com.rlirette.tools.rentalhelper.model.dao.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceEventRepository extends JpaRepository<Source, Integer> {

    Optional<Source> findByNameIgnoreCase(String name);
}
