package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AlarmknopfHilferufDto {

  private final String id = UUID.randomUUID().toString();

  private String trackerId;

  public AlarmknopfHilferufDto(final String trackerId) {
    this.trackerId = trackerId;
  }
}
