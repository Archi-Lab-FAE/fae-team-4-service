package de.th.koeln.archilab.fae.faeteam4service.dto.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {

  @Getter @Setter private BreitengradDto breitengrad;

  @Getter @Setter private LaengengradDto laengengrad;

  public PositionDto(double laengengrad, double breitengrad) {
    this.laengengrad = new LaengengradDto(laengengrad);
    this.breitengrad = new BreitengradDto(breitengrad);
  }
}
