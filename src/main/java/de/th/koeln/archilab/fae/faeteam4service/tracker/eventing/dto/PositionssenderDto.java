package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionssenderDto implements Serializable {

  private String id;
  private Date letzteWartung;

}