package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackerServiceTest {

  private TrackerRepository mockTrackerRepository;
  private TrackerService trackerService;
  private List<Tracker> listOfTestTrackers;
  private Tracker tracker1;

  private static final String TRACKER_1_ID = "tracker1";
  private static final String INCOMING_ID = "someID";

  @Before
  public void setUp() {
    mockTrackerRepository = Mockito.mock(TrackerRepository.class);
    trackerService = new TrackerService(mockTrackerRepository);

    tracker1 = createTestTrackerWith(TRACKER_1_ID, 50.94239, 6.97128);
    Tracker tracker2 = createTestTrackerWith("tracker2", 50.94232, 6.97138);
    Tracker tracker3 = createTestTrackerWith("tracker3", 50.94234, 6.97136);
    Tracker tracker4 = createTestTrackerWith("tracker5", 50.94239, 6.97138);

    listOfTestTrackers = new ArrayList<>();
    listOfTestTrackers.add(tracker1);
    listOfTestTrackers.add(tracker2);
    listOfTestTrackers.add(tracker3);
    listOfTestTrackers.add(tracker4);

    when(mockTrackerRepository.findAll()).thenReturn(listOfTestTrackers);
  }

  @Test
  public void updatingATrackerShouldSaveTheNewTrackerToRepository() {
    Tracker trackerThatIsReturnedByRepositoryAfterSaving = new Tracker();
    Tracker newTracker = new Tracker(INCOMING_ID);
    when(mockTrackerRepository.save(newTracker))
        .thenReturn(trackerThatIsReturnedByRepositoryAfterSaving);

    Tracker savedTracker = trackerService.updateOrCreateTracker(newTracker);

    assertThat(savedTracker, equalTo(trackerThatIsReturnedByRepositoryAfterSaving));
  }

  @Test
  public void deletingATrackerShouldReturnFalseIfThereIsNoTrackerWithTheGivenId() {
    when(mockTrackerRepository.findById(TRACKER_1_ID)).thenReturn(Optional.empty());

    boolean hasDeletingWorked = trackerService.deleteTracker(TRACKER_1_ID);

    assertThat(hasDeletingWorked, equalTo(false));
  }

  @Test
  public void deletingATrackerShouldReturnTrueIfTheTrackerIsDeleted() {
    when(mockTrackerRepository.findById(TRACKER_1_ID)).thenReturn(Optional.of(tracker1));

    boolean hasDeletingWorked = trackerService.deleteTracker(TRACKER_1_ID);

    verify(mockTrackerRepository).delete(tracker1);
    assertThat(hasDeletingWorked, equalTo(true));
  }

  @Test
  public void givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProximity_2_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(50.94232, 6.97139);
    double radius = 5.0;

    List<Tracker> trackerInProximity = trackerService.getTrackerInProximityOf(alarmknopf, radius);

    assertTrue(trackerInProximity.contains(listOfTestTrackers.get(1)));
    assertTrue(trackerInProximity.contains(listOfTestTrackers.get(2)));
  }

  @Test
  public void givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProximity_0_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(78.0649, 14.213);
    double radius = 5.0;

    List<Tracker> trackerInProximity = trackerService.getTrackerInProximityOf(alarmknopf, radius);

    assertThat(trackerInProximity, is(empty()));
  }

  @Test
  public void
      givenAlarmknopfAndTrackerRepoAndRadiusThenReturnTrackerInProxy_IncorrectRadius_0_InRange() {
    Alarmknopf alarmknopf = createTestAlarmknopfWithPosition(50.94232, 6.97139);
    double radius = -5.0;

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
