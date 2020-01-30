package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Error {

  @Id private String id;

  private LocalDateTime thrownAt;

  private String text;

  public Error(Exception exception) {
    id = UUID.randomUUID().toString();
    thrownAt = LocalDateTime.now();

    text =
        exception.toString() + " " + exception.getMessage() + " " + exception.getLocalizedMessage();
  }
}
