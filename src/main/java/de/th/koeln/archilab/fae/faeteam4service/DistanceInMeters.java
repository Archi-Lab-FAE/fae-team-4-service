package de.th.koeln.archilab.fae.faeteam4service;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DistanceInMeters {

  @NonNull private double distance;

  public boolean isSmallerOrEqualAs(DistanceInMeters otherDistanceInMeters) {
    return distance <= otherDistanceInMeters.distance;
  }
}
