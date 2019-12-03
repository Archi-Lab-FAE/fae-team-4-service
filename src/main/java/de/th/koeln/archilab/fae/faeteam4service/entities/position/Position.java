package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalPosition;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Position {

  @Embedded
  @Getter
  @Setter
  private Breitengrad breitengrad;

  @Embedded
  @Getter
  @Setter
  private Laengengrad laengengrad;

  public Position(double laengengrad, double breitengrad) {
    this.laengengrad = new Laengengrad(laengengrad);
    this.breitengrad = new Breitengrad(breitengrad);
  }

  public DistanceInMeters getDistanceInMetersTo(final Position otherPosition) {
    GeodeticCurve geodeticCurve = getGeodeticCurve(this, otherPosition);

    double tempDistanceInMeters = geodeticCurve.getEllipsoidalDistance();

    return new DistanceInMeters(tempDistanceInMeters);
  }

  private GeodeticCurve getGeodeticCurve(
      final Position thisPosition, final Position otherPosition) {
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    double elevation = 0.0;
    Ellipsoid representingModelOfEarth = Ellipsoid.WGS84;

    double otherBreitengrad = otherPosition.breitengrad.getBreitengradDezimal();
    double otherLaengengrad = otherPosition.laengengrad.getLaengengradDezimal();

    double thisBreitengrad = thisPosition.breitengrad.getBreitengradDezimal();
    double thisLaengengrad = thisPosition.laengengrad.getLaengengradDezimal();

    GlobalPosition thisGlobalPosition =
        new GlobalPosition(thisBreitengrad, thisLaengengrad, elevation);
    GlobalPosition otherGlobalPosition =
        new GlobalPosition(otherBreitengrad, otherLaengengrad, elevation);

    return geoCalc.calculateGeodeticCurve(
        representingModelOfEarth, otherGlobalPosition, thisGlobalPosition);
  }
}
