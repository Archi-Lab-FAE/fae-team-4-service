package de.th.koeln.archilab.fae.faeteam4service.dto;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DementiellErkranktePersonDto {

  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private String trackerId;

  @Getter
  @Setter
  private Position position;
}
