package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import java.util.UUID;
import lombok.Getter;

public class AlarmknopfHilferuf {

  @Getter
  private final String id = UUID.randomUUID().toString();

  @Getter
  private final String trackerId;

  public AlarmknopfHilferuf(String trackerId) {
    this.trackerId = trackerId;
  }
}
