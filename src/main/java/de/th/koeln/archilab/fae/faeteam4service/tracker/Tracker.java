package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
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

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }

  Boolean isInProximityOfPosition(Position otherPosition, DistanceInMeters maxDistanceInMeters) {
    if (position == null) return false;
    DistanceInMeters distanceToPosition = position.getDistanceInMetersTo(otherPosition);
    return distanceToPosition.isSmallerOrEqualAs(maxDistanceInMeters);
  }
}
