package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Error {

  @Id private String id;

  private String text;

  public Error(Exception exception) {
    id = UUID.randomUUID().toString();

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);

    text = pw.toString();

    pw.close();
    try {
      sw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
