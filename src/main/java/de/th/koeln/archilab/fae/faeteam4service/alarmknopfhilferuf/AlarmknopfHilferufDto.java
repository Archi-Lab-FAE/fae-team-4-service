package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmknopfHilferufDto {

  private final String id = UUID.randomUUID().toString();

  private final String trackerId;

  @JsonCreator
  public AlarmknopfHilferufDto(final String trackerId) {
    this.trackerId = trackerId;
  }

}
