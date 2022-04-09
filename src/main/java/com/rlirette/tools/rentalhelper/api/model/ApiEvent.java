package com.rlirette.tools.rentalhelper.api.model;

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
@Table(schema = "api")
public class ApiEvent implements Serializable {

    @GeneratedValue
    @Id
    private Long eventId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String code;

    private String comment;
}
