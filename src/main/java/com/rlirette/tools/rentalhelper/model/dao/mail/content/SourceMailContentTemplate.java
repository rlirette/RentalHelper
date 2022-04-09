package com.rlirette.tools.rentalhelper.model.dao.mail.content;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "batch")
public class SourceMailContentTemplate implements Serializable {

    @GeneratedValue
    @Id
    private Long id;

    private String templateName;

    @Column(length = 1000)
    private String title;

    @Column(length = 2000)
    private String body;

    @Column(length = 2000)
    private String item;
}
