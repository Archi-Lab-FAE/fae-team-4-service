package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfKafkaGateway;
import org.springframework.stereotype.Component;

@Component
public class AlarmknopfKafkaPublisher {

  private AlarmknopfKafkaGateway alarmknopfKafkaGateway;
  private AlarmknopfEventFactory alarmknopfEventFactory;

  public AlarmknopfKafkaPublisher(
      AlarmknopfKafkaGateway alarmknopfKafkaGateway,
      AlarmknopfEventFactory alarmknopfEventFactory) {
    this.alarmknopfKafkaGateway = alarmknopfKafkaGateway;
    this.alarmknopfEventFactory = alarmknopfEventFactory;
  }

  public void publishDeletedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf);
    alarmknopfKafkaGateway.publishAlarmknopfEvent(event);
  }

  public void publishUpdatedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf);
    alarmknopfKafkaGateway.publishAlarmknopfEvent(event);
  }
}
