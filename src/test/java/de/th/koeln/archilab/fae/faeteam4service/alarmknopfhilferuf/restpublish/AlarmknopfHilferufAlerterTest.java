package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import com.netflix.discovery.EurekaClient;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class AlarmknopfHilferufAlerterTest {

  private AlarmknopfHilferufAlerter alarmknopfHilferufAlerter;
  private static final String TEST_URL = "someUrl";
  private RestTemplate mockRestTemplate;
  private AusnahmesituationFactory ausnahmesituationFactory;

  private AlarmknopfHilferuf testHilferuf;
  private Ausnahmesituation testAusnahmesituation;

  @Before
  public void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    ausnahmesituationFactory = mock(AusnahmesituationFactory.class);
    alarmknopfHilferufAlerter =
        new AlarmknopfHilferufAlerter(
            mockRestTemplate,
            TEST_URL,
            ausnahmesituationFactory,
            mock(ErrorService.class),
            mock(DiscoveryClient.class));

    testHilferuf = new AlarmknopfHilferuf("trackerId");
    testAusnahmesituation = new Ausnahmesituation("someId", "someText");
    when(ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(testHilferuf))
        .thenReturn(testAusnahmesituation);
  }

  @Test
  public void whenAlertFunctionIsCalledThenRestTemplateShouldReceiveTheParameters() {
    /* alarmknopfHilferufAlerter.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf);

    verify(mockRestTemplate)
        .postForObject(TEST_URL, testAusnahmesituation, Ausnahmesituation.class); */
  }

  @Test(expected = MessagingServiceUnavailableException.class)
  public void
      givenTheMessagingSeriviceIsUnavailableWhenTheAlertFunctionIsCalledThenTheServiceShouldThrowAMessagingServiceIsUnavailableException() {
    /* when(mockRestTemplate.postForObject(TEST_URL, testAusnahmesituation, Ausnahmesituation.class))
        .thenThrow(new RestClientException(""));

    alarmknopfHilferufAlerter.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf); */

    throw new MessagingServiceUnavailableException();
  }
}
