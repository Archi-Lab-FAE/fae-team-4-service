package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AlarmknopfHilferufAlerter {

  private RestTemplate restTemplate;
  private String messagingSystemUrl;
  private AusnahmesituationFactory ausnahmesituationFactory;

  public AlarmknopfHilferufAlerter(
      RestTemplate restTemplate,
      @Value("${messagingSystem.url}") final String messagingSystemUrl,
      AusnahmesituationFactory ausnahmesituationFactory) {
    this.restTemplate = restTemplate;
    this.messagingSystemUrl = messagingSystemUrl;
    this.ausnahmesituationFactory = ausnahmesituationFactory;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    try {
      restTemplate.postForObject(messagingSystemUrl, ausnahmesituation, Ausnahmesituation.class);
    } catch (RestClientException e) {
      throw e;
    }
  }
}
