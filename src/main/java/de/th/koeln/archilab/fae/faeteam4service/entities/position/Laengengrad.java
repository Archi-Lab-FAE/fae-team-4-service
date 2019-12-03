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

  //TODO: Breiten& Längengrad haben den selben sanity check - zusammenführen?
  // DB update script notwendig?
  Laengengrad(double laengengradDezimal) {
    if (laengengradDezimal < 0 || laengengradDezimal > 180) {
      throw new IllegalArgumentException("Längengrad muss zwischen 0 und 180 liegen.");
    }

    this.laengengradDezimal = laengengradDezimal;
  }
}
