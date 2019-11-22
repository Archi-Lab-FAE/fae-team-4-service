package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Embeddable
public class Laengengrad {

  @Getter
  @Setter
  @NonNull
  private double laengengrad;
}
