package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.registrierung;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionssenderDto implements Serializable {

  @NonNull
  private String id;
  private Date letzteWartung;

}