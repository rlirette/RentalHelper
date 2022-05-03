package com.rlirette.tools.rentalhelper.model.dao;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "batch")
public class Event implements Serializable {

    @GeneratedValue
    @Id
    private Long eventId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String code;

    @Transient
    private EventStatus status;
}
