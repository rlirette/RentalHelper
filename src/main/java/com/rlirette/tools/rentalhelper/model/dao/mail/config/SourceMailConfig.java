package com.rlirette.tools.rentalhelper.model.dao.mail.config;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "batch")
public class SourceMailConfig implements Serializable {

    @GeneratedValue
    @Id
    private Long id;

    private String host;

    private String port;
}
