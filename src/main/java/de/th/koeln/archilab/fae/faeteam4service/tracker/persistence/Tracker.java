package de.th.koeln.archilab.fae.faeteam4service.tracker.persistence;

import de.th.koeln.archilab.fae.faeteam4service.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tracker {

  @Id private String id;

  @Embedded @Nullable private Position position;

  public Tracker(String id) {
    this.id = id;
  }

  public Tracker(String id, @Nullable Double laengengrad, @Nullable Double breitengrad) {
    this(id);

    if (laengengrad != null && breitengrad != null) {
      this.position = new Position(breitengrad, laengengrad);
    }
  }

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }

  public Boolean hasNoPosition() {
    return position == null;
  }
}
