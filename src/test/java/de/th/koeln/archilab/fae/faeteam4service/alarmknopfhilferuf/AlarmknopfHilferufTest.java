package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.Test;

public class AlarmknopfHilferufTest {

  @Test
  public void whenAlarmknopfHilferufIsCreated_thenUuidIsGenerated() {
    String trackerId = "trackerId";
    AlarmknopfHilferuf alarmknopfHilferuf = new AlarmknopfHilferuf(trackerId);

    assertThat(alarmknopfHilferuf.getId(), is(notNullValue()));
  }
}