package com.rlirette.tools.rentalhelper.model.dao;

import com.rlirette.tools.rentalhelper.model.dao.ics.SourceIcs;
import com.rlirette.tools.rentalhelper.model.dao.mail.SourceMail;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "batch")
public class Source implements Serializable {

    @GeneratedValue
    @Id
    private Long sourceId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinColumn
    private Set<Event> events;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private SourceMail sourceMail;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Set<SourceIcs> sourcesIcs;

    private boolean isEnable;
}
