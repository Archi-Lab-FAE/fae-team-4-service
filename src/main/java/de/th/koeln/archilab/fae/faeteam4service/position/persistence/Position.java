package de.th.koeln.archilab.fae.faeteam4service.position.persistence;

import de.th.koeln.archilab.fae.faeteam4service.common.Distance;
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
import org.springframework.lang.Nullable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Position {

  @Embedded
  @Nullable
  private Breitengrad breitengrad;

  @Embedded
  @Nullable
  private Laengengrad laengengrad;

  public Position(double breitengrad, double laengengrad) {
    this.breitengrad = new Breitengrad(breitengrad);
    this.laengengrad = new Laengengrad(laengengrad);
  }

  public Distance getDistanceInMetersTo(final Position otherPosition) {
    GeodeticCurve geodeticCurve = getGeodeticCurve(this, otherPosition);

    double tempDistanceInMeters = geodeticCurve.getEllipsoidalDistance();

    return new Distance(tempDistanceInMeters);
  }

  private GeodeticCurve getGeodeticCurve(
      final Position thisPosition, final Position otherPosition) {
    GeodeticCalculator geoCalc = new GeodeticCalculator();
    double elevation = 0.0;
    Ellipsoid representingModelOfEarth = Ellipsoid.WGS84;

    assert otherPosition.breitengrad != null;
    double otherBreitengrad = otherPosition.breitengrad.getBreitengradDezimal();
    assert otherPosition.laengengrad != null;
    double otherLaengengrad = otherPosition.laengengrad.getLaengengradDezimal();

    assert thisPosition.breitengrad != null;
    double thisBreitengrad = thisPosition.breitengrad.getBreitengradDezimal();
    assert thisPosition.laengengrad != null;
    double thisLaengengrad = thisPosition.laengengrad.getLaengengradDezimal();

    GlobalPosition thisGlobalPosition =
        new GlobalPosition(thisBreitengrad, thisLaengengrad, elevation);
    GlobalPosition otherGlobalPosition =
        new GlobalPosition(otherBreitengrad, otherLaengengrad, elevation);

    return geoCalc.calculateGeodeticCurve(
        representingModelOfEarth, otherGlobalPosition, thisGlobalPosition);
  }
}
