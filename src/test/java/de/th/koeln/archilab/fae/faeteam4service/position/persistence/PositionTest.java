package de.th.koeln.archilab.fae.faeteam4service.position.persistence;

import static org.junit.Assert.assertEquals;

import de.th.koeln.archilab.fae.faeteam4service.Distance;
import org.junit.Test;

public class PositionTest {

  @Test
  public void givenTwoPositionsTheCorrespondingDistanceInMetersShouldBeReturned() {
    Position bahnhofKoelnMesse = getPositionFromBreitengradAndLaengengrad(50.9417015, 6.9738248);
    Position deutzerFreiheit = getPositionFromBreitengradAndLaengengrad(50.93684, 6.97523);
    Distance expectedDistanceBetweenBahnhofKoelnMesseAndDeutzerFreiheit =
        new Distance(549.7730131900543);
    assertDistanceInMetersBetweenTwoPositions(bahnhofKoelnMesse, deutzerFreiheit,
        expectedDistanceBetweenBahnhofKoelnMesseAndDeutzerFreiheit);

    Position koelnerDomSuedturm = getPositionFromBreitengradAndLaengengrad(50.94112, 6.95728);
    Position koelnerDomNordturm = getPositionFromBreitengradAndLaengengrad(50.94144, 6.95727);
    Distance expectedDistanceBetweenKoelnerDomSuedturmAndKoelnerDomNordturm =
        new Distance(35.60602665403109);
    assertDistanceInMetersBetweenTwoPositions(koelnerDomSuedturm, koelnerDomNordturm,
        expectedDistanceBetweenKoelnerDomSuedturmAndKoelnerDomNordturm);

    Position coffeeFellows = getPositionFromBreitengradAndLaengengrad(50.9432138, 6.9583567);
    Position pizzaHut = getPositionFromBreitengradAndLaengengrad(50.9431768, 6.9583621);
    Distance expectedDistanceBetweenCoffeeFellowsAndPizzaHut =
        new Distance(4.133606288021513);
    assertDistanceInMetersBetweenTwoPositions(coffeeFellows, pizzaHut,
        expectedDistanceBetweenCoffeeFellowsAndPizzaHut);
  }

  private void assertDistanceInMetersBetweenTwoPositions(final Position thisPosition,
      final Position otherPosition, final Distance expectedDistance) {
    double toleratedDelta = 0.001;

    Distance actualDistance = thisPosition.getDistanceInMetersTo(otherPosition);
    assertEquals(expectedDistance.getDistanceInMeters(), actualDistance.getDistanceInMeters(),
        toleratedDelta);
  }

  private Position getPositionFromBreitengradAndLaengengrad(final double breitengradToSet,
      final double laengengradToSet) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengradDezimal(breitengradToSet);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengradDezimal(laengengradToSet);

    return new Position(breitengrad, laengengrad);
  }
}
