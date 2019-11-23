package de.th.koeln.archilab.fae.faeteam4service.entities.external;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import javax.persistence.Embedded;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
