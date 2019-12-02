package de.th.koeln.archilab.fae.faeteam4service.entities;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Alarmknopf {

  @Id
  @Getter
  @Setter
  @NonNull
  private String id;

  @Embedded
  @Getter
  @Setter
  @NonNull
  private Position position;
}
