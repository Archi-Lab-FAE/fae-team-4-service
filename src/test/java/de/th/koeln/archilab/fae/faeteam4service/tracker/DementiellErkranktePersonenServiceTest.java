package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePerson;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonRepository;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonenService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class DementiellErkranktePersonenServiceTest {

  private final DementiellErkranktePersonRepository mockPersonRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;
  private List<DementiellErkranktePerson> personen;

  public DementiellErkranktePersonenServiceTest() {
    mockPersonRepository = Mockito.mock(DementiellErkranktePersonRepository.class);
    dementiellErkranktePersonenService =
        new DementiellErkranktePersonenService(mockPersonRepository);
  }

  @Before
  public void generateTestPersonsForRepository() {
    DementiellErkranktePerson person1 = generatePersonWithPosition("person1", 50.94239, 6.97128);
    DementiellErkranktePerson person2 = generatePersonWithPosition("person2", 50.94232, 6.97138);
    DementiellErkranktePerson person3 = generatePersonWithPosition("person3", 50.94234, 6.97136);
    DementiellErkranktePerson person4 = generatePersonWithPosition("person4", 50.94239, 6.97138);

    personen = new ArrayList<>();
    personen.add(person1);
    personen.add(person2);
    personen.add(person3);
    personen.add(person4);
  }

  @Test
  public void
      givenAlarmknopfAndDementiellErkranktePersonenRepoAndRadiusThenReturnPersonenInProximity_2_InRange() {
    Alarmknopf alarmknopf = generateKnopfWithIdAndNameAndPosition("id", "myName", 50.94232, 6.97139);
    double radius = 5.0;

    when(mockPersonRepository.findAll()).thenReturn(personen);

    List<DementiellErkranktePerson> personenInProximity =
        dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radius);
    assertTrue(personenInProximity.contains(personen.get(1)));
    assertTrue(personenInProximity.contains(personen.get(2)));
  }

  @Test
  public void
      givenAlarmknopfAndDementiellErkranktePersonenRepoAndRadiusThenReturnPersonenInProximity_0_InRange() {
    Alarmknopf alarmknopf = generateKnopfWithIdAndNameAndPosition("id", "myName", 78.0649, 14.213);
    double radius = 5.0;

    when(mockPersonRepository.findAll()).thenReturn(personen);

    List<DementiellErkranktePerson> personenInProximity =
        dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radius);
    assertThat(personenInProximity, is(empty()));
  }

  @Test
  public void
      givenAlarmknopfAndDementiellErkranktePersonenRepoAndRadiusThenReturnPersonenInProxy_IncorrectRadius_0_InRange() {
    Alarmknopf alarmknopf = generateKnopfWithIdAndNameAndPosition("id", "myName", 50.94232, 6.97139);
    double radius = -5.0;

    when(mockPersonRepository.findAll()).thenReturn(personen);

    List<DementiellErkranktePerson> personenInProximity =
        dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radius);
    assertThat(personenInProximity, is(empty()));
  }

  private Alarmknopf generateKnopfWithIdAndNameAndPosition(
      final String id, final String name, final double latitude, final double longitude) {
    Position position = getPositionFromLatitudeAndLongitude(latitude, longitude);
    return new Alarmknopf(id, name, position);
  }

  private DementiellErkranktePerson generatePersonWithPosition(
      final String trackerId, final double latitude, final double longitude) {
    Position position = getPositionFromLatitudeAndLongitude(latitude, longitude);

    return new DementiellErkranktePerson("id", trackerId, position);
  }

  private Position getPositionFromLatitudeAndLongitude(
      final double latitude, final double longitude) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengradDezimal(latitude);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengradDezimal(longitude);

    return new Position(breitengrad, laengengrad);
  }
}