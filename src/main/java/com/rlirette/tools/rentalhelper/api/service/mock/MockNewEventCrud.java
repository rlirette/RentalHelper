package com.rlirette.tools.rentalhelper.api.service.mock;

import com.rlirette.tools.rentalhelper.model.dao.Source;
import com.rlirette.tools.rentalhelper.model.dao.ics.SourceIcs;
import com.rlirette.tools.rentalhelper.model.dao.mail.SourceMail;
import com.rlirette.tools.rentalhelper.model.dao.mail.config.SourceMailConfig;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentHeaderRecipient;
import com.rlirette.tools.rentalhelper.model.dao.mail.content.SourceMailContentTemplate;

import java.util.Set;

public class MockNewEventCrud {

    public static Source buildNew(){
        return Source.builder()
                .sourcesIcs(Set.of(getSourceIcsAirbnb(), getSourceIcsBooking(), getSourceIcsRentalHelper()))
                .isEnable(true)
                .name("LaCherry404")
                .sourceMail(getSourceMail())
                .build();
    }

    private static SourceIcs getSourceIcsBooking() {
        SourceIcs sourceIcsBooking = SourceIcs.builder()
                .icsCalendarUri("https://admin.booking.com/hotel/hoteladmin/ical.html?t=2f7ae153-f10d-4758-aa73-a86b5c3dd8c7")
                .name("booking")
                .build();
        return sourceIcsBooking;
    }

    private static SourceIcs getSourceIcsAirbnb() {
        return SourceIcs.builder()
                    .icsCalendarUri("https://www.airbnb.fr/calendar/ical/47392472.ics?s=2cd07617ddef4e492a150929da680e7f")
                    .name("airbnb")
                    .build();
    }

    private static SourceIcs getSourceIcsRentalHelper() {
        return SourceIcs.builder()
                .icsCalendarUri("http://localhost:8080/api/ics")
                .name("rentalHelper")
                .build();
    }

    private static SourceMail getSourceMail() {
        SourceMail sourceMail = SourceMail.builder()
                .sourceMailConfig(getSourceMailConfig())
                .sourceMailContentTemplate(Set.of(getSourceMailContentTemplateCreate(), getSourceMailContentTemplateUpdate()))
                .transmitter("raphael.lirette@gmail.com")
                .recipients(Set.of(getSourceMailContentHeaderRecipient()))
                .build();
        return sourceMail;
    }

    private static SourceMailContentHeaderRecipient getSourceMailContentHeaderRecipient() {
        SourceMailContentHeaderRecipient sourceMailContentHeaderRecipient = SourceMailContentHeaderRecipient.builder()
                .recipient("mc.clean.net@gmail.com")
                .build();
        return sourceMailContentHeaderRecipient;
    }

    private static SourceMailContentTemplate getSourceMailContentTemplateUpdate() {
        SourceMailContentTemplate sourceMailContentTemplateUpdateReservations = SourceMailContentTemplate.builder()
                .title("[Black Diamond][404] Mise à jour planning (message automatique)")
                .body("<html><body>" +
                        "Bonjour MC Clean," +
                        "<br/><br/>" +
                        "Certains &eacute;v&egrave;nements ont &eacute;t&eacute; modifi&eacute;s. Ci dessous, le planning à jour :" +
                        "<br/>" +
                        "<table class=\"styled-table\" style=\"text-align: center;border-collapse: collapse;margin: 25px 0;font-size: 0.9em;font-family: sans-serif;min-width: 400px;box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);\">" +
                        "<thead><tr style=\"background-color: #117A65;color: #ffffff;text-align: left;\">" +
                        "<th style=\"padding: 12px 15px;\">Entr&eacute;e</th><th style=\"padding: 12px 15px;\">Sortie</th><th style=\"padding: 12px 15px;\">Code bo&icirc;te &agrave; cl&eacute;s</th><th style=\"padding: 12px 15px;\">Modification</th></tr></thead><tbody><events/></tbody></table>" +
                        "<br/>" +
                        "Cordialement," +
                        "<br/>" +
                        "<h3 style=\"font-family: sans-serif;color: #117A65;\">Raphael LIRETTE</h3>" +
                        "</body></html>")
                .item("<tr style=\"border-bottom: 1px solid #dddddd;\"><td style=\"padding: 12px 15px;\"><startDate/></td><td style=\"padding: 12px 15px;\"><endDate/></td><td style=\"padding: 12px 15px;\"><keyBoxCode/></td><td style=\"padding: 12px 15px;\"><status/></td></tr>")
                .templateName("update")
                .build();
        return sourceMailContentTemplateUpdateReservations;
    }

    private static SourceMailContentTemplate getSourceMailContentTemplateCreate() {
        return SourceMailContentTemplate.builder()
                    .title("[Black Diamond][404] Planning - <next_month/> (message automatique)")
                    .body("<html><body>" +
                            "Bonjour MC Clean," +
                            "<br/><br/>" +
                            "Ci-dessous le planning du mois prochain :" +
                            "<br/>" +
                            "<table class=\"styled-table\" style=\"text-align: center;border-collapse: collapse;margin: 25px 0;font-size: 0.9em;font-family: sans-serif;min-width: 400px;box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);\"><thead><tr style=\"background-color: #117A65;color: #ffffff;text-align: left;\">" +
                            "<th style=\"padding: 12px 15px;\">Entr&eacute;e</th><th style=\"padding: 12px 15px;\">Sortie</th><th style=\"padding: 12px 15px;\">Code bo&icirc;te &agrave; cl&eacute;s</th></tr></thead>" +
                            "<tbody><events/></tbody>" +
                            "</table><br/>" +
                            "Cordialement," +
                            "<br/>" +
                            "<h3 style=\"font-family: sans-serif;color: #117A65;\">Raphael LIRETTE</h3></body></html>")
                    .item("<tr style=\"border-bottom: 1px solid #dddddd;\"><td style=\"padding: 12px 15px;\"><startDate/></td><td style=\"padding: 12px 15px;\"><endDate/></td><td style=\"padding: 12px 15px;\"><keyBoxCode/></td></tr>")
                    .templateName("create")
                    .build();
    }

    private static SourceMailConfig getSourceMailConfig() {
        SourceMailConfig sourceMailConfig = SourceMailConfig.builder()
                .host("smtp.gmail.com")
                .port("587")
                .build();
        return sourceMailConfig;
    }

}