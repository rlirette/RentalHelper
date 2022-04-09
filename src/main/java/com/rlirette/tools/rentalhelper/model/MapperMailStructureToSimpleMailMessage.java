package com.rlirette.tools.rentalhelper.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.mail.SimpleMailMessage;

@Mapper(config = MapperConfiguration.class)
public interface MapperMailStructureToSimpleMailMessage {

    @Mapping(source = "transmitter", target = "from")
    @Mapping(source = "recipient", target = "to")
    @Mapping(source = "title", target = "subject")
    @Mapping(source = "body", target = "text")
    SimpleMailMessage map(MailStructure source);
}
