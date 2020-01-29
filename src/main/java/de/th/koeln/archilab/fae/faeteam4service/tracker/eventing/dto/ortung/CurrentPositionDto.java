package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.ortung;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrentPositionDto implements Serializable {

  @NonNull
  private double latitude;
  @NonNull
  private double longitude;
  @NonNull
  private double altitude;
}
