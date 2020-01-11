package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.rest;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlarmknopfHilferufApiClient {

  private RestTemplate restTemplate;

  public AlarmknopfHilferufApiClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void alertServiceOf(List<AlarmknopfHilferuf> alarmknopfHilferufe) {
    //TODO: Implement rest connection
  }
}
