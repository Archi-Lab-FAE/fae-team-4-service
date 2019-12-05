package de.th.koeln.archilab.fae.faeteam4service.tracker.persistence;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tracker {

  @Id
  private String id;

  @Embedded
  @Nullable
  private Position position;

  public Tracker(String id) {
    this.id = id;
  }

  public Tracker(String id, @Nullable Double laengengrad, @Nullable Double breitengrad) {
    this(id);

    if (laengengrad != null && breitengrad != null) {
      this.position = new Position(laengengrad, breitengrad);
    }
  }

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }

  public Boolean isInProximityOfPosition(
      Position otherPosition, DistanceInMeters maxDistanceInMeters) {
    if (position == null) {
      return false;
    }
    DistanceInMeters distanceToPosition = position.getDistanceInMetersTo(otherPosition);
    return distanceToPosition.isSmallerOrEqualAs(maxDistanceInMeters);
  }
}
