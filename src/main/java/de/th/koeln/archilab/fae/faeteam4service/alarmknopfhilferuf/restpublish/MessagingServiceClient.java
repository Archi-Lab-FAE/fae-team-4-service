package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class MessagingServiceClient {

  private final RestTemplate restTemplate;
  private final AusnahmesituationFactory ausnahmesituationFactory;
  private final String messagingServiceId;

  public MessagingServiceClient(
      final RestTemplate restTemplate,
      final AusnahmesituationFactory ausnahmesituationFactory,
      @Value("${messagingSystem.eurekaId}") String messagingServiceId) {
    this.restTemplate = restTemplate;
    this.ausnahmesituationFactory = ausnahmesituationFactory;
    this.messagingServiceId = messagingServiceId;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    try {
      restTemplate.postForObject(
          createMessagingServiceUrl(), ausnahmesituation, Ausnahmesituation.class);
    } catch (RestClientException e) {
      throw new CouldNotReachMessagingServiceException();
    }
  }

  private String createMessagingServiceUrl() {
    return "http://" + messagingServiceId + "/ausnahmesituation";
  }
}
