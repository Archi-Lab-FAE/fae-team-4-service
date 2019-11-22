package de.th.koeln.archilab.fae.faeteam4service.entities;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
public class DementiellErkranktePerson {

  @Id
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private String trackerId;

  @Embedded
  @Getter
  @Setter
  private Position position;

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }
}
