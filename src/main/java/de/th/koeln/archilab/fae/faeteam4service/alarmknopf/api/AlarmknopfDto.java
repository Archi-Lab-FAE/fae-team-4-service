package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "Alarmknopf")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class AlarmknopfDto {

  private String id;

  @NonNull
  private String name;

  @NonNull
  private PositionDto position;

  @NonNull
  private double meldungsrelevanterRadiusInMetern;

}
