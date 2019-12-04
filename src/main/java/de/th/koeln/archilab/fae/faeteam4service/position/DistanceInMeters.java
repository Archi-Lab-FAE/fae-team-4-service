package de.th.koeln.archilab.fae.faeteam4service.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class DistanceInMeters {

  @NonNull
  private double distance;

}
