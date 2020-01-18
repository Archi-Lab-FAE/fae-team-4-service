package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlarmknopfHilferufAlerter {

  private RestTemplate restTemplate;
  private String messagingSystemUrl;

  public AlarmknopfHilferufAlerter(
      RestTemplate restTemplate, @Value("${messagingSystem.url}") final String messagingSystemUrl) {
    this.restTemplate = restTemplate;
    this.messagingSystemUrl = messagingSystemUrl;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    restTemplate.postForObject(messagingSystemUrl, alarmknopfHilferuf, String.class);
  }
}
