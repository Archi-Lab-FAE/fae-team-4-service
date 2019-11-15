package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
public class PersonPosition {

  @Id
  @Getter
  @Setter
  @NonNull
  String trackerId;

  @Embedded
  @Getter
  @Setter
  @NonNull
  Position position;
}
