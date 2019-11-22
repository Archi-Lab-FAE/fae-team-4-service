package de.th.koeln.archilab.fae.faeteam4service.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DementiellErkranktePersonTest {

    private static final String TEST_ID = "someID";

    @Test
    public void dementiellErkranktePersonShouldCreateCorrectHilferuf() {
        DementiellErkranktePerson dementiellErkranktePerson = new DementiellErkranktePerson();
        dementiellErkranktePerson.setId(TEST_ID);

        AlarmknopfHilferuf createdHilferuf = dementiellErkranktePerson.createAlarmknopfHilferuf();

        assertThat(createdHilferuf.getDementiellErkranktePersonId(), equalTo(TEST_ID));
    }
}