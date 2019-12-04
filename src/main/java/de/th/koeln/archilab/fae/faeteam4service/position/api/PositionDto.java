package de.th.koeln.archilab.fae.faeteam4service.position.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

  private BreitengradDto breitengrad;

  private LaengengradDto laengengrad;

  public PositionDto(double laengengrad, double breitengrad) {
    this.laengengrad = new LaengengradDto(laengengrad);
    this.breitengrad = new BreitengradDto(breitengrad);
  }
}
