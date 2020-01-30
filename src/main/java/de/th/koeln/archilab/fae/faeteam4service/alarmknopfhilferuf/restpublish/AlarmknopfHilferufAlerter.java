package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class AlarmknopfHilferufAlerter {

  private RestTemplate restTemplate;
  private AusnahmesituationFactory ausnahmesituationFactory;
  private ErrorService errorService;

  public AlarmknopfHilferufAlerter(
      RestTemplate restTemplate,
      AusnahmesituationFactory ausnahmesituationFactory,
      ErrorService errorService) {
    this.restTemplate = restTemplate;
    this.ausnahmesituationFactory = ausnahmesituationFactory;
    this.errorService = errorService;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    try {
      restTemplate.postForObject(
          "http://fae-team-3-service/ausnahmesituation",
          ausnahmesituation,
          Ausnahmesituation.class);
    } catch (HttpClientErrorException e) {
      errorService.persistString("Status: " + e.getStatusText());
    } catch (RestClientException e) {
      errorService.persistException(e);
      throw new CouldNotReachMessagingServiceException();
    }
  }
}
