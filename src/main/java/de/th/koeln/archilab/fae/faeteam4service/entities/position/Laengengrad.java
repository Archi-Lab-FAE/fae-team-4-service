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
  private double laengengradVal;

  //TODO: Breiten& Längengrad haben den selben sanity check - zusammenführen?
  // DB update script notwendig?
  Laengengrad(double laengengradVal) {
    if (laengengradVal < 0 || laengengradVal > 180) {
      throw new IllegalArgumentException("Längengrad muss zwischen 0 und 180 liegen.");
    }

    this.laengengradVal = laengengradVal;
  }
}
