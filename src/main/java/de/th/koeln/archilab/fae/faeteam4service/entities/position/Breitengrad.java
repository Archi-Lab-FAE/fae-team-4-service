package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Embeddable
public class Breitengrad {

  @Getter
  @Setter
  @NonNull
  private double breitengrad;
}
