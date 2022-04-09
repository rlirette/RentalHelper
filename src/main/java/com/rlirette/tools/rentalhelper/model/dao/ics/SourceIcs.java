package com.rlirette.tools.rentalhelper.model.dao.ics;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "batch")
public class SourceIcs {

    @GeneratedValue
    @Id
    private Long sourceId;

    private String name;

    @Column(length = 2000)
    private String icsCalendarUri;
}
