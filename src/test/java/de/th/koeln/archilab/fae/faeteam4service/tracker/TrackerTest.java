package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TrackerTest {

  private static final String TEST_ID = "someID";

  private Tracker tracker;

  @Before
  public void setup() {
    tracker = new Tracker(TEST_ID, new Position(0,0));
  }

  @Test
  public void createAlarmknopfHilferufshouldCreateCorrectHilferuf() {
    AlarmknopfHilferuf createdHilferuf = tracker.createAlarmknopfHilferuf();

    assertThat(createdHilferuf.getTrackerId(), equalTo(TEST_ID));
  }

  @Test
  public void shouldBeInProximityIfPositionsAreNearEnough() {
    Position otherPositionThatIsInRange = new Position(0,0);

    boolean isPersonInProximity = tracker.isInProximityOfPosition(otherPositionThatIsInRange, new DistanceInMeters(1.0));

    assertThat(isPersonInProximity, equalTo(true));
  }

  @Test
  public void shouldNotBeInProximityOfPositionThatsTooFarAway() {
    Position positionThatIsTooFarAway = new Position(10,10);

    boolean isPersonInProximity =
        tracker.isInProximityOfPosition(positionThatIsTooFarAway, new DistanceInMeters(1.0));

    assertThat(isPersonInProximity, equalTo(false));
  }
}
