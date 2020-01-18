package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api.AlarmknopfDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonPropertyOrder({"id", "key", "version", "timestamp", "type", "payload"})
class AlarmknopfEvent {
  private final String id = UUID.randomUUID().toString();
  private String key;
  private Long version;
  private final LocalDateTime timestamp = LocalDateTime.now();
  private String type = "";
  private AlarmknopfDto payload;
}
