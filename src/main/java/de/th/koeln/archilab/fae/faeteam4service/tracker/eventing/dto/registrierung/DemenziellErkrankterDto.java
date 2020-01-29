package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.registrierung;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemenziellErkrankterDto implements Serializable {

  private String id;
  private String name;
  private String vorname;
  @NonNull
  private Boolean zustimmung;
  private List<KontaktpersonDto> kontaktpersonen;
  private List<PositionssenderDto> positionssender;


}