package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.UUID;

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

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);

    text = sw.toString();

    pw.close();
    try {
      sw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
