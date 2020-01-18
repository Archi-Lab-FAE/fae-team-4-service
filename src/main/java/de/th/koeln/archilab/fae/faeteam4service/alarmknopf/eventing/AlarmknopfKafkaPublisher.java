package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing.KafkaGateway;
import org.springframework.stereotype.Component;

@Component
public class AlarmknopfKafkaPublisher {

  private KafkaGateway kafkaGateway;
  private AlarmknopfEventFactory alarmknopfEventFactory;

  public AlarmknopfKafkaPublisher(
      KafkaGateway kafkaGateway, AlarmknopfEventFactory alarmknopfEventFactory) {
    this.kafkaGateway = kafkaGateway;
    this.alarmknopfEventFactory = alarmknopfEventFactory;
  }

  public void publishDeletedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf);
    kafkaGateway.publishAlarmknopfEvent(event);
  }

  public void publishUpdatedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf);
    kafkaGateway.publishAlarmknopfEvent(event);
  }
}
