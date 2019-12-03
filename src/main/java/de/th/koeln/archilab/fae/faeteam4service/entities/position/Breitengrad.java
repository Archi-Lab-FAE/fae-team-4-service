package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class Breitengrad {

  @Getter
  @Setter
  @NonNull
  private double breitengradDezimal;

  //TODO: Breiten& Längengrad haben den selben sanity check - zusammenführen?
  // DB update script notwendig?
  Breitengrad(double breitengradDezimal) {
    if (breitengradDezimal < 0 || breitengradDezimal > 180) {
      throw new IllegalArgumentException("Breitengrad muss zwischen 0 und 180 liegen.");
    }

    this.breitengradDezimal = breitengradDezimal;
  }
}
