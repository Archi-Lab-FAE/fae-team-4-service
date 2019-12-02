package de.th.koeln.archilab.fae.faeteam4service.entities;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import java.util.Optional;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
public class DementiellErkranktePerson {

  @Id
  @Getter
  @Setter
  private String id;

  @Getter
  @Setter
  private String trackerId;

  @Embedded
  @Getter
  @Setter
  private Position position;

  public AlarmknopfHilferuf createAlarmknopfHilferuf() {
    return new AlarmknopfHilferuf(id);
  }

  public Boolean isInProximityOfPosition(Position otherPosition, double maxDistanceInMeters) {
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
