package de.th.koeln.archilab.fae.faeteam4service.position.persistence;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Laengengrad {

  @NonNull
  private double laengengradDezimal;

  Laengengrad(double laengengradDezimal) {
    if (laengengradDezimal < -85 || laengengradDezimal > 85) {
      throw new IllegalArgumentException("Längengrad muss zwischen -85 und 85 liegen.");
    }

    this.laengengradDezimal = laengengradDezimal;
  }
}
