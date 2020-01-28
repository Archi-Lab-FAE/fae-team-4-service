package de.th.koeln.archilab.fae.faeteam4service.position.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "Position")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

  private double breitengrad;

  private double laengengrad;
}
