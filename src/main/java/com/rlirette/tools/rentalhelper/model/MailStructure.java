package com.rlirette.tools.rentalhelper.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {

    private String title;
    private String transmitter;
    private String[] recipient;
    private String[] copy;
    private String body;
}
