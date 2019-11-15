package de.th.koeln.archilab.fae.faeteam4service.entities;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
public class Knopf {

  @Id
  @Getter
  @Setter
  @NonNull
  String id;

  @Embedded
  @Getter
  @Setter
  @NonNull
  Position position;
}
