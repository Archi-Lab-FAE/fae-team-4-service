package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import java.util.Optional;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DementiellErkranktePerson {

  @Id
  private String id;

  private String trackerId;

  @Embedded
  private Position position;

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }

  Boolean isInProximityOfPosition(Position otherPosition, double maxDistanceInMeters) {
    Optional<DistanceInMeters> distanceToPosition = getDistanceToPosition(otherPosition);
    return distanceToPosition
        .filter(distanceInMeters -> distanceInMeters.getDistance() <= maxDistanceInMeters)
        .isPresent();
  }

  private Optional<DistanceInMeters> getDistanceToPosition(Position otherPosition) {
    if (position == null) {
      return Optional.empty();
    }
    return Optional.of(position.getDistanceInMetersTo(otherPosition));
  }
}
