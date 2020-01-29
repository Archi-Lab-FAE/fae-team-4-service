package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.ortung;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrtungTrackerDto implements Serializable {

  @NonNull
  private String trackerId;
  private CurrentPositionDto currentPosition;
}
