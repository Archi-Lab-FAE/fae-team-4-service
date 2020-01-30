package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.springframework.stereotype.Component;

@Component
public class AusnahmesituationFactory {

  Ausnahmesituation createAusnahmesituationFromAlarmknopfHilferuf(
      AlarmknopfHilferuf alarmknopfHilferuf) {
    return new Ausnahmesituation(
        alarmknopfHilferuf.getTrackerId(), "Alarmknopf gedrückt! Hilfe benötigt!");
  }
}
