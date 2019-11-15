package de.th.koeln.archilab.fae.faeteam4service.entities;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.PersonPosition;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
public class Person {

  @Id
  String id;

  String trackerId;

  @Embedded
  @Getter
  @Setter
  @NonNull
  PersonPosition personPosition;
}
