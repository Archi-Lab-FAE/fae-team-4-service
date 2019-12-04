package de.th.koeln.archilab.fae.faeteam4service.position.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreitengradDto {

  @NonNull private double breitengradDezimal;

}
