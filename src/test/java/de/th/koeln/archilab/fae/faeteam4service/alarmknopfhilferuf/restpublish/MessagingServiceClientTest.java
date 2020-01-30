package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

public class MessagingServiceClientTest {

  private MessagingServiceClient messagingServiceClient;
  private MessagingServiceFeignClient messagingServiceFeignClient;

  private AlarmknopfHilferuf testHilferuf;
  private Ausnahmesituation testAusnahmesituation;

  @Before
  public void setUp() {
    messagingServiceFeignClient = mock(MessagingServiceFeignClient.class);
    AusnahmesituationFactory ausnahmesituationFactory = mock(AusnahmesituationFactory.class);
    messagingServiceClient =
        new MessagingServiceClient(ausnahmesituationFactory, messagingServiceFeignClient);

    testHilferuf = new AlarmknopfHilferuf("trackerId");
    testAusnahmesituation = new Ausnahmesituation("someId", "someText");
    when(ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(testHilferuf))
        .thenReturn(testAusnahmesituation);
  }

  @Test
  public void shouldCallMessagingServiceWithAusnahmesituationWhenHilferufIsGiven() {
    messagingServiceClient.alertMessagingSystemAboutAlarmknopfHilferuf(testHilferuf);

    verify(messagingServiceFeignClient).createAusnahmeSituation(testAusnahmesituation);
  }
}
