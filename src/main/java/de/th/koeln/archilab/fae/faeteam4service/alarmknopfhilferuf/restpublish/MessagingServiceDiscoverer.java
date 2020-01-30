package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class MessagingServiceDiscoverer {

  private final String messagingServiceId;
  private final DiscoveryClient discoveryClient;

  public MessagingServiceDiscoverer(
      @Value("${messagingSystem.eurekaId}") final String messagingServiceId,
      DiscoveryClient discoveryClient) {
    this.messagingServiceId = messagingServiceId;
    this.discoveryClient = discoveryClient;
  }

  URI discoverMessagingServiceUri() {
    ServiceInstance serviceInstance =
        discoveryClient.getInstances(messagingServiceId).stream()
            .findFirst()
            .orElseThrow(NoInstanceOfMessagingServiceFoundException::new);
    return serviceInstance.getUri();
  }
}
