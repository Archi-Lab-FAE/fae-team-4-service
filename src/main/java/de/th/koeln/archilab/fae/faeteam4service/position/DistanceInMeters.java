package de.th.koeln.archilab.fae.faeteam4service.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
public class DistanceInMeters {

  @NonNull
  private double distance;

}
