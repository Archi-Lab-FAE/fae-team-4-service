package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
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

public class TrackerServiceTest {

  private final TrackerRepository mockTrackerRepository;
  private final TrackerService trackerService;
  private List<Tracker> listOfTestTrackers;

  public TrackerServiceTest() {
    mockTrackerRepository = Mockito.mock(TrackerRepository.class);
    trackerService = new TrackerService(mockTrackerRepository);
  }

  @Before
  public void setUp() {
    Tracker person1 = createTestTrackerWith("tracker1", 50.94239, 6.97128);
    Tracker person2 = createTestTrackerWith("tracker2", 50.94232, 6.97138);
    Tracker person3 = createTestTrackerWith("tracker3", 50.94234, 6.97136);
    Tracker person4 = createTestTrackerWith("tracker5", 50.94239, 6.97138);

    listOfTestTrackers = new ArrayList<>();
    listOfTestTrackers.add(person1);
    listOfTestTrackers.add(person2);
    listOfTestTrackers.add(person3);
    listOfTestTrackers.add(person4);
  }

  @Test
  public void givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProximity_2_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(50.94232, 6.97139);
    double radius = 5.0;

    when(mockTrackerRepository.findAll()).thenReturn(listOfTestTrackers);

    List<Tracker> trackerInProximity = trackerService.getTrackerInProximityOf(alarmknopf, radius);
    assertTrue(trackerInProximity.contains(listOfTestTrackers.get(1)));
    assertTrue(trackerInProximity.contains(listOfTestTrackers.get(2)));
  }

  @Test
  public void givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProximity_0_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(78.0649, 14.213);
    double radius = 5.0;

    when(mockTrackerRepository.findAll()).thenReturn(listOfTestTrackers);

    List<Tracker> trackerInProximity = trackerService.getTrackerInProximityOf(alarmknopf, radius);
    assertThat(trackerInProximity, is(empty()));
  }

  @Test
  public void
      givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProxy_IncorrectRadius_0_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(50.94232, 6.97139);
    double radius = -5.0;

    when(mockTrackerRepository.findAll()).thenReturn(listOfTestTrackers);

    List<Tracker> trackerInProximity = trackerService.getTrackerInProximityOf(alarmknopf, radius);
    assertThat(trackerInProximity, is(empty()));
  }

  private Alarmknopf createTestAlarmknopfWithPosition(
      final double latitude, final double longitude) {
    return new Alarmknopf("id", "myName", new Position(longitude, latitude));
  }

  private Tracker createTestTrackerWith(
      final String trackerId, final double latitude, final double longitude) {
    return new Tracker(trackerId, new Position(longitude, latitude));
  }
}
