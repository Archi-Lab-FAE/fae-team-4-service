package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
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
    messagingServiceFeignClient.createAusnahmeSituation(ausnahmesituation);
  }
}
