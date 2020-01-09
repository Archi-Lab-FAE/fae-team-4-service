package de.th.koeln.archilab.fae.faeteam4service;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Distance {

  private double distanceInMeters;

  public boolean isSmallerOrEqualAs(Distance otherDistance) {
    return distanceInMeters <= otherDistance.distanceInMeters;
  }
}
