package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.DemenziellErkrankterDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "key", "version", "timestamp", "type", "payload"})
public class DemenziellErkrankterEvent {

  @NonNull
  private String id;
  @NonNull
  private String key;
  @NonNull
  private Long version;
  @NonNull
  private LocalDateTime timestamp;
  @NonNull
  private String type;
  @NonNull
  private DemenziellErkrankterDto payload;

}
