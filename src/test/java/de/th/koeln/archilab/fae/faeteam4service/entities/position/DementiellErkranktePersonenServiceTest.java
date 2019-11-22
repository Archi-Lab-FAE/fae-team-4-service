package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePersonRepository;
import de.th.koeln.archilab.fae.faeteam4service.service.DementiellErkranktePersonenService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class DementiellErkranktePersonenServiceTest {

  private final DementiellErkranktePersonRepository mockPersonRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;

  public DementiellErkranktePersonenServiceTest() {
    mockPersonRepository = Mockito.mock(DementiellErkranktePersonRepository.class);
    dementiellErkranktePersonenService = new DementiellErkranktePersonenService(mockPersonRepository);
  }

  @Test
  public void givenAlarmknopfAndDementiellErkranktePersonenRepoAndRadiusThenReturnPersonenInProxy() {
    DementiellErkranktePerson person1 = generatePersonWithPosition("person1", 50.94239,6.97128);
    DementiellErkranktePerson person2 = generatePersonWithPosition("person2", 50.94232,6.97138);
    DementiellErkranktePerson person3 = generatePersonWithPosition("person3", 50.94234,6.97136);
    DementiellErkranktePerson person4 = generatePersonWithPosition("person4", 50.94239,6.97138);

    List<DementiellErkranktePerson> personen = new ArrayList<>();
    personen.add(person1);
    personen.add(person2);
    personen.add(person3);
    personen.add(person4);

    Alarmknopf alarmknopf = generateKnopfWithIdAndPosition("id", 50.94232,6.97139);
    double radius = 5.0;

    when(mockPersonRepository.findAll()).thenReturn(personen);

    List<DementiellErkranktePerson> personenInProximity = dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radius);
    assertEquals(2, personenInProximity.size());
  }

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

  private Alarmknopf generateKnopfWithIdAndPosition(final String id, final double latitude, final double longitude) {
    Position position = getPositionFromLatitudeAndLongitude(latitude, longitude);
    return new Alarmknopf(id, position);
  }

  private DementiellErkranktePerson generatePersonWithPosition(final String trackerId, final double latitude, final double longitude) {
    Position position = getPositionFromLatitudeAndLongitude(latitude,longitude);

    return new DementiellErkranktePerson("id", trackerId, position);
  }
}