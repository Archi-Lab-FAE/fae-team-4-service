package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence;

import de.th.koeln.archilab.fae.faeteam4service.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AlarmknopfTest {

  private static final String TEST_ID = "someId";

  private Alarmknopf alarmknopf;
  private Tracker tracker;

  @Before
  public void setUp() {
    alarmknopf = new Alarmknopf();
    alarmknopf.setMeldungsrelevanterRadius(new DistanceInMeters(10));
    alarmknopf.setPosition(new Position(0, 0));

    tracker = new Tracker();
    tracker.setPosition(new Position(0, 0));
  }

  @Test
  public void shouldNotBeInProximityIfItHasNoPosition() {
    Tracker trackerWithoutPosition = new Tracker(TEST_ID);

    assertThat(alarmknopf.isTrackerInProximity(trackerWithoutPosition), equalTo(false));
  }

  @Test
  public void shouldBeInProximityIfPositionsAreNearEnough() {
    boolean isTrackerInProximity = alarmknopf.isTrackerInProximity(tracker);

    assertThat(isTrackerInProximity, equalTo(true));
  }

  @Test
  public void shouldNotBeInProximityOfPositionThatsTooFarAway() {
    Position positionThatIsTooFarAway = new Position(10, 10);
    tracker.setPosition(positionThatIsTooFarAway);

    boolean isTrackerInProximity = alarmknopf.isTrackerInProximity(tracker);

    assertThat(isTrackerInProximity, equalTo(false));
  }
}
