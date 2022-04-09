package com.rlirette.tools.rentalhelper.model.dao.mail;

import com.rlirette.tools.rentalhelper.model.dao.mail.config.SourceMailConfig;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentHeaderCopy;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentHeaderRecipient;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentTemplate;
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
public class SourceMail implements Serializable {

    @GeneratedValue
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Set<SourceMailContentTemplate> sourceMailContentTemplate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private SourceMailConfig sourceMailConfig;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Set<SourceMailContentHeaderRecipient> recipients;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Set<SourceMailContentHeaderCopy> mailCopies;

    private String transmitter;
}
