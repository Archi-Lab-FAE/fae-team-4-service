package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DementiellErkranktePersonDto {

  private String id;
  private String trackerId;
  private Position position;
}
