package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class MessagingServiceClientTest {

  private MessagingServiceClient messagingServiceClient;
  private RestTemplate mockRestTemplate;
  private static final String TEST_MESSAGING_SERVICE_ID = "someId";

  private AlarmknopfHilferuf testHilferuf;
  private Ausnahmesituation testAusnahmesituation;

  @Before
  public void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    AusnahmesituationFactory ausnahmesituationFactory = mock(AusnahmesituationFactory.class);
    messagingServiceClient =
        new MessagingServiceClient(
            mockRestTemplate, ausnahmesituationFactory, TEST_MESSAGING_SERVICE_ID);

    testHilferuf = new AlarmknopfHilferuf("trackerId");
    testAusnahmesituation = new Ausnahmesituation("someId", "someText");
    when(ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(testHilferuf))
        .thenReturn(testAusnahmesituation);
  }

  @Test
  public void shouldCallMessagingServiceWithAusnahmesituationWhenHilferufIsGiven() {
    messagingServiceClient.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf);

    verify(mockRestTemplate)
        .postForObject(
            "http://" + TEST_MESSAGING_SERVICE_ID + "/ausnahmesituation",
            testAusnahmesituation,
            Ausnahmesituation.class);
  }

  @Test(expected = CouldNotReachMessagingServiceException.class)
  public void
      shouldThrowCouldNotReachMessagingServiceExceptionWhenRestTemplateThrowsRestClientException() {
    when(mockRestTemplate.postForObject(
            anyString(),
            eq(testAusnahmesituation),
            eq(Ausnahmesituation.class)))
        .thenThrow(new RestClientException(""));

    messagingServiceClient.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf);
  }
}
