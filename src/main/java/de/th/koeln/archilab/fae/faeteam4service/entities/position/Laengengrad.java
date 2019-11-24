package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Laengengrad {

  @Getter @Setter @NonNull private double laengengrad;

  //TODO: Breiten& Längengrad haben den selben sanity check - zusammenführen? DB update script notwendig?
  Laengengrad(double laengengrad) {
    if (laengengrad < 0 || laengengrad > 180) {
      throw new IllegalArgumentException("Längengrad muss zwischen 0 und 180 liegen.");
    }

    this.laengengrad = laengengrad;
  }
}
