package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
class AlarmknopfHilferufDto {

  private final String id = UUID.randomUUID().toString();

  private String trackerId;

  @JsonCreator
  AlarmknopfHilferufDto(final String trackerId) {
    this.trackerId = trackerId;
  }
}
