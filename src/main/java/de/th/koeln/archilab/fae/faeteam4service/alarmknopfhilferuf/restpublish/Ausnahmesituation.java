package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class Ausnahmesituation {

  private String positionssenderId;
  private String text;
}
