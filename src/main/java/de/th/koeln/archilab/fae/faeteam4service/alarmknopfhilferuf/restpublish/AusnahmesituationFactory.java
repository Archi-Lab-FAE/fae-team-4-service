package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.springframework.stereotype.Component;

@Component
public class AusnahmesituationFactory {

  Ausnahmesituation createAusnahmesituationFromAlarmknopfHilferuf(
      AlarmknopfHilferuf alarmknopfHilferuf, Alarmknopf alarmknopf) {
    return new Ausnahmesituation(
        alarmknopfHilferuf.getTrackerId(),
        "Sehr geehrte(r) Dame/Herr/Pflegeheimmitarbeiter(in),"
            + " ihr/e Angehörige(r)/Patient(in) benötigt vielleicht Hilfe."
            + " Bitte begeben sie sich zu ihm/ihr. Der Name des Ortes lautet: "
            + alarmknopf.getName());
  }
}
