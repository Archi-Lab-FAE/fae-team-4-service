package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class Laengengrad {

  @Getter
  @Setter
  @NonNull
  private double laengengradDezimal;

  Laengengrad(double laengengradDezimal) {
    if (laengengradDezimal < -85 || laengengradDezimal > 85) {
      throw new IllegalArgumentException("Längengrad muss zwischen -85 und 85 liegen.");
    }

    this.laengengradDezimal = laengengradDezimal;
  }
}
