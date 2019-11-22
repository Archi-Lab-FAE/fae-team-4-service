package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import static org.junit.Assert.assertEquals;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import org.junit.Test;

public class PositionTest {

  @Test
  public void givenTwoPositionsTheCorrespondingDistanceInMetersShouldBeReturned() {
    Position bahnhofKoelnMesse = getPositionFromLatitudeAndLongitude(50.9417015, 6.9738248);
    Position deutzerFreiheit = getPositionFromLatitudeAndLongitude(50.93684, 6.97523);
    DistanceInMeters expectedDistanceBetweenBahnhofKoelnMesseAndDeutzerFreiheit =
        new DistanceInMeters(549.7730131900543);
    assertDistanceInMetersBetweenTwoPositions(bahnhofKoelnMesse, deutzerFreiheit,
        expectedDistanceBetweenBahnhofKoelnMesseAndDeutzerFreiheit);

    Position koelnerDomSuedturm = getPositionFromLatitudeAndLongitude(50.94112, 6.95728);
    Position koelnerDomNordturm = getPositionFromLatitudeAndLongitude(50.94144, 6.95727);
    DistanceInMeters expectedDistanceBetweenKoelnerDomSuedturmAndKoelnerDomNordturm =
        new DistanceInMeters(35.60602665403109);
    assertDistanceInMetersBetweenTwoPositions(koelnerDomSuedturm, koelnerDomNordturm,
        expectedDistanceBetweenKoelnerDomSuedturmAndKoelnerDomNordturm);

    Position coffeeFellows = getPositionFromLatitudeAndLongitude(50.9432138, 6.9583567);
    Position pizzaHut = getPositionFromLatitudeAndLongitude(50.9431768, 6.9583621);
    DistanceInMeters expectedDistanceBetweenCoffeeFellowsAndPizzaHut =
        new DistanceInMeters(4.133606288021513);
    assertDistanceInMetersBetweenTwoPositions(coffeeFellows, pizzaHut,
        expectedDistanceBetweenCoffeeFellowsAndPizzaHut);
  }

  private void assertDistanceInMetersBetweenTwoPositions(final Position thisPosition,
      final Position otherPosition, final DistanceInMeters expectedDistanceInMeters) {
    double toleratedDelta = 0.001;

    DistanceInMeters actualDistanceInMeters = thisPosition.getDistanceInMetersTo(otherPosition);
    assertEquals(expectedDistanceInMeters.getDistance(), actualDistanceInMeters.getDistance(),
        toleratedDelta);
  }

  private Position getPositionFromLatitudeAndLongitude(final double latitude,
      final double longitude) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengrad(latitude);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengrad(longitude);

    return new Position(breitengrad, laengengrad);
  }
}
