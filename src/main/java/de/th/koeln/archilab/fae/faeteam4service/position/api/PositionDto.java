package de.th.koeln.archilab.fae.faeteam4service.position.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Position")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

  private double breitengrad;

  private double laengengrad;

}
