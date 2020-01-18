package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence;

import de.th.koeln.archilab.fae.faeteam4service.Distance;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Hidden
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarmknopf {

  @Id @NonNull private String id;

  @NonNull private String name;

  @Embedded @NonNull private Position position;

  @Embedded @NonNull private Distance meldungsrelevanterRadius;

  public boolean isTrackerInProximity(Tracker tracker) {
    if (Boolean.TRUE.equals(tracker.hasNoPosition())) {
      return false;
    }

    Distance distanceToTracker = position.getDistanceInMetersTo(tracker.getPosition());
    return distanceToTracker.isSmallerOrEqualAs(meldungsrelevanterRadius);
  }
}
