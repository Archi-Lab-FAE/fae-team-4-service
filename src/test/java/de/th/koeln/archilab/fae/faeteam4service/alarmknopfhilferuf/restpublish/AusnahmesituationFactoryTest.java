package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AusnahmesituationFactoryTest {

  private AusnahmesituationFactory ausnahmesituationFactory;
  private AlarmknopfHilferuf alarmknopfHilferuf;
  private static final String TRACKER_ID = "someId";

  @Before
  public void setup() {
    ausnahmesituationFactory = new AusnahmesituationFactory();
    alarmknopfHilferuf = new AlarmknopfHilferuf(TRACKER_ID);
  }

  @Test
  public void shouldCreateAusnahmesituationCorrectly() {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    assertThat(ausnahmesituation.getPositionssenderId(), equalTo(TRACKER_ID));
  }
}
