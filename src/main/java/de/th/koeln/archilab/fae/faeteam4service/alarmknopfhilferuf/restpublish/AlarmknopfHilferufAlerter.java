package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AlarmknopfHilferufAlerter {

  private RestTemplate restTemplate;
  private String messagingSystemUrl;
  private AusnahmesituationFactory ausnahmesituationFactory;
  private ErrorService errorService;
  private DiscoveryClient discoveryClient;

  public AlarmknopfHilferufAlerter(
      RestTemplate restTemplate,
      @Value("${messagingSystem.eurekaId}") final String messagingSystemUrl,
      AusnahmesituationFactory ausnahmesituationFactory,
      ErrorService errorService,
      DiscoveryClient discoveryClient) {
    this.restTemplate = restTemplate;
    this.messagingSystemUrl = messagingSystemUrl;
    this.ausnahmesituationFactory = ausnahmesituationFactory;
    this.errorService = errorService;
    this.discoveryClient = discoveryClient;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    Optional<ServiceInstance> instance =
        discoveryClient.getInstances(messagingSystemUrl).stream().findFirst();

    instance.ifPresent(
        serviceInstance -> {
          try {
            restTemplate.postForObject(
                serviceInstance.getUri().toString() + "/ausnahmesituation",
                ausnahmesituation,
                Ausnahmesituation.class);
          } catch (RestClientException e) {
            errorService.persistException(e);
            throw new MessagingServiceUnavailableException();
          }
        });
  }
}
