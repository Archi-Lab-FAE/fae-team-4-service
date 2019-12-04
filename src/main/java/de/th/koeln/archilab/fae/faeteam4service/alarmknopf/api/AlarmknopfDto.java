package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AlarmknopfDto {

  @Getter
  @Setter
  @NonNull
  private String id;

  @Getter
  @Setter
  @NonNull
  private String name;

  @Getter
  @Setter
  @NonNull
  private PositionDto position;
}