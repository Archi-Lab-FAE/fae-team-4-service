package de.th.koeln.archilab.fae.faeteam4service;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Distance {

  private double distanceInMeters;

  public boolean isSmallerOrEqualAs(Distance otherDistance) {
    return distanceInMeters <= otherDistance.distanceInMeters;
  }
}
