package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import java.util.UUID;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;

@Embeddable
public class Position {

  @Id
  UUID id;

  @Embedded
  private Laengengrad laengengrad;

  @Embedded
  private Breitengrad breitengrad;

  public DistanceInMeters getDistanceInMetersTo(final Position otherPosition) {
    double thisLaengengrad = laengengrad.getLaegengrad();
    double thisBreitengrad = breitengrad.getBreitengrad();

    double otherLaengengrad = otherPosition.laengengrad.getLaegengrad();
    double otherBreitengrad = otherPosition.breitengrad.getBreitengrad();

    double latDistance = Math.toRadians(otherLaengengrad - thisLaengengrad);
    double lonDistance = Math.toRadians(otherBreitengrad - thisBreitengrad);

    DistanceInMeters distanceInMeters = calculateDistanceInMeters(otherLaengengrad, latDistance,
        lonDistance);

    return distanceInMeters;
  }

  private DistanceInMeters calculateDistanceInMeters(double otherLaengengrad, double latDistance,
      double lonDistance) {

    final int radiusOfEarth = 6371;
    double thisLaengengrad = laengengrad.getLaegengrad();

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(thisLaengengrad)) * Math.cos(Math.toRadians(otherLaengengrad))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    double distance = radiusOfEarth * c * 1000; // convert to meters

    distance = Math.pow(distance, 2);
    return new DistanceInMeters(distance);
  }
}
