package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "id", "key", "version", "timestamp", "type", "payload" })
public class HilferufEvent {

  private final String id = UUID.randomUUID().toString();

  private String key;

  private Long version = 1L;

  private final LocalDateTime timestamp = LocalDateTime.now();

  private String type = "alarmknopf-hilferuf-ausgeloest";

  private AlarmknopfHilferufDto payload;

  @JsonCreator
  public HilferufEvent(final AlarmknopfHilferufDto payload) {
    this.key = payload.getId();
    this.payload = payload;
  }
}
