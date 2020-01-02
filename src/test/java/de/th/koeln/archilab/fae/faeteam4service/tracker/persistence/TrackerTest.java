package de.th.koeln.archilab.fae.faeteam4service.tracker.persistence;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TrackerTest {

  private static final String TEST_ID = "someID";
  private static final double TEST_BREITENGRAD = 107.6;
  private static final double TEST_LAENGENGRAD = 11.11;

  private Tracker tracker;

  @Before
  public void setup() {
    tracker = new Tracker(TEST_ID, new Position(0, 0));
  }

  @Test
  public void shouldInitializeWithoutPositionIfBreitengradIsNull() {
    Tracker tracker = new Tracker(TEST_ID, TEST_LAENGENGRAD, null);

    assertThat(tracker.getId(), equalTo(TEST_ID));
    assertThat(tracker.getPosition(), nullValue());
  }

  @Test
  public void shouldInitializeWithoutPositionIfLaengengradIsNull() {
    Tracker tracker = new Tracker(TEST_ID, null, TEST_BREITENGRAD);

    assertThat(tracker.getId(), equalTo(TEST_ID));
    assertThat(tracker.getPosition(), nullValue());
  }

  @Test
  public void shouldInitializeWithPositionIfLaengengradAndBreitengradAreNotNull() {
    Tracker tracker = new Tracker(TEST_ID, TEST_LAENGENGRAD, TEST_BREITENGRAD);

    assertThat(tracker.getId(), equalTo(TEST_ID));
    Position position = tracker.getPosition();
    assertThat(position, notNullValue());
    assertThat(position.getLaengengrad().getLaengengradDezimal(), equalTo(TEST_LAENGENGRAD));
    assertThat(position.getBreitengrad().getBreitengradDezimal(), equalTo(TEST_BREITENGRAD));
  }

  @Test
  public void createAlarmknopfHilferufshouldCreateCorrectHilferuf() {
    AlarmknopfHilferuf createdHilferuf = tracker.createAlarmknopfHilferuf();

    assertThat(createdHilferuf.getTrackerId(), equalTo(TEST_ID));
  }

  @Test
  public void hasNoPositionShouldBeTrueIfNoPositionIsSaved() {
    tracker.setPosition(null);

    assertThat(tracker.hasNoPosition(), equalTo(true));
  }

  @Test
  public void hasNoPositionShouldBeFalseIfPositionIsSaved() {
    assertThat(tracker.hasNoPosition(), equalTo(false));
  }
}
