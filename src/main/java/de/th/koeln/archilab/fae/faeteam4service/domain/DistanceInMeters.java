package de.th.koeln.archilab.fae.faeteam4service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class DistanceInMeters {

  @Getter
  @Setter
  @NonNull
  private double distance;

}
