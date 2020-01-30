package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class MessagingServiceClient {

  private final AusnahmesituationFactory ausnahmesituationFactory;
  private final MessagingServiceFeignClient messagingServiceFeignClient;

  public MessagingServiceClient(
      final AusnahmesituationFactory ausnahmesituationFactory,
      MessagingServiceFeignClient messagingServiceFeignClient) {
    this.ausnahmesituationFactory = ausnahmesituationFactory;
    this.messagingServiceFeignClient = messagingServiceFeignClient;
  }

  public void alertMessagingSystemAboutAlarmknopfHilferuf(AlarmknopfHilferuf alarmknopfHilferuf) {
    Ausnahmesituation ausnahmesituation =
        ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(alarmknopfHilferuf);

    try {
      messagingServiceFeignClient.createAusnahmesituation(ausnahmesituation);
    } catch (FeignException e) {

      String rundownBuilder =
          "Failed request to adjacent service"
              + "Status: "
              + e.status()
              + "Method: "
              + e.request().httpMethod()
              + "Url: "
              + e.request().url()
              + "Body: "
              + e.request().requestBody().asString();

      throw new IllegalStateException(rundownBuilder);
    }
  }
}
