package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.registrierung;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KontaktpersonDto implements Serializable {

  private String id;
  private String name;
  private String vorname;
  private String telefonnummer;
  private Boolean aktiv;

}
