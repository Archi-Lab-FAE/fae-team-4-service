package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HilferufEvent {

  private final String id = UUID.randomUUID().toString();

  private String key;

  static final Long VERSION = 1L;

  private final LocalDateTime timestamp = LocalDateTime.now();

  static final String TYPE = "alarmknopf-hilferuf-ausgeloest";

  private AlarmknopfHilferufDto payload;

  @JsonCreator
  public HilferufEvent(final AlarmknopfHilferufDto payload) {
    this.key = payload.getId();
    this.payload = payload;
  }

  public byte[] getPayload(final ObjectMapper objectMapper) throws JsonProcessingException {
    return objectMapper.writeValueAsBytes(payload);
  }
}
