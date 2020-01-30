package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class AlarmknopfHilferufAlerter {

  private RestTemplate restTemplate;
  private AusnahmesituationFactory ausnahmesituationFactory;
  private ErrorService errorService;
  private MessagingServiceDiscoverer messagingServiceDiscoverer;

  public AlarmknopfHilferufAlerter(
      RestTemplate restTemplate,
      AusnahmesituationFactory ausnahmesituationFactory,
      ErrorService errorService,
      MessagingServiceDiscoverer messagingServiceDiscoverer) {
    this.restTemplate = restTemplate;
    this.ausnahmesituationFactory = ausnahmesituationFactory;
    this.errorService = errorService;
    this.messagingServiceDiscoverer = messagingServiceDiscoverer;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    try {
      restTemplate.postForObject(
          getMessagingServiceUrl(), ausnahmesituation, Ausnahmesituation.class);
    } catch (RestClientException e) {
      errorService.persistException(e);
      throw new CouldNotReachMessagingServiceException();
    } catch (URISyntaxException e) {
      errorService.persistException(e);
    }
  }

  private URI getMessagingServiceUrl() throws URISyntaxException {
    return new URI(messagingServiceDiscoverer.discoverMessagingServiceUri() + "/ausnahmesituation");
  }
}
