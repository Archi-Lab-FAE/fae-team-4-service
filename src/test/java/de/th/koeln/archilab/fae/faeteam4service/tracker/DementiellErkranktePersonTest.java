package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePerson;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DementiellErkranktePersonTest {

  private static final String TEST_ID = "someID";

  private DementiellErkranktePerson dementiellErkranktePerson;

  @Before
  public void setup() {
    dementiellErkranktePerson = new DementiellErkranktePerson(TEST_ID, null, null);
  }

  @Test
  public void dementiellErkranktePersonShouldCreateCorrectHilferuf() {
    AlarmknopfHilferuf createdHilferuf = dementiellErkranktePerson.createAlarmknopfHilferuf();

    assertThat(createdHilferuf.getDementiellErkranktePersonId(), equalTo(TEST_ID));
  }

  @Test
  public void shouldBeNotInProximityOfPositionIfNoPositionIsSaved() {
    Position otherPosition = new Position(0,0);

    boolean isPersonInProximity =
        dementiellErkranktePerson.isInProximityOfPosition(otherPosition, 1.0);

    assertThat(isPersonInProximity, equalTo(false));
  }

  @Test
  public void shouldBeInProximityIfPositionsAreNearEnough() {
    Position position = new Position(0,0);
    dementiellErkranktePerson.setPosition(position);

    boolean isPersonInProximity = dementiellErkranktePerson.isInProximityOfPosition(position, 1.0);

    assertThat(isPersonInProximity, equalTo(true));
  }

  @Test
  public void shouldNotBeInProximityOfPositionThatsTooFarAway() {
    Position position = new Position(0,0);
    dementiellErkranktePerson.setPosition(position);

    Position positionThatIsTooFarAway = new Position(10,10);

    boolean isPersonInProximity =
        dementiellErkranktePerson.isInProximityOfPosition(positionThatIsTooFarAway, 1.0);

    assertThat(isPersonInProximity, equalTo(false));
  }
}
