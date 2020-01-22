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
public class Breitengrad {

  @NonNull
  private double breitengradDezimal;

  Breitengrad(double breitengradDezimal) {
    if (breitengradDezimal < -180 || breitengradDezimal > 180) {
      throw new IllegalArgumentException("Breitengrad muss zwischen -180 und 180 liegen.");
    }

    this.breitengradDezimal = breitengradDezimal;
  }
}
