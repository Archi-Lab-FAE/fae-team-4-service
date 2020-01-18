package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AlarmknopfHilferufAlerterTest {

  private AlarmknopfHilferufAlerter alarmknopfHilferufAlerter;
  private static final String TEST_URL = "someUrl";
  private RestTemplate mockRestTemplate;

  @Before
  public void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    alarmknopfHilferufAlerter = new AlarmknopfHilferufAlerter(mockRestTemplate, TEST_URL);
  }

  @Test
  public void whenAlertFunctionIsCalledThenRestTemplateShouldReceiveTheParameters() {
    AlarmknopfHilferuf testHilferuf = new AlarmknopfHilferuf("trackerId");

    alarmknopfHilferufAlerter.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf);

    verify(mockRestTemplate).postForObject(TEST_URL, testHilferuf, String.class);
  }
}
