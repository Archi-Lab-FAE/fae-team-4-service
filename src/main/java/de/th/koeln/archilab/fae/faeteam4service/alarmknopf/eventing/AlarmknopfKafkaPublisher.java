package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfHilferufKafkaGateway;
import org.springframework.stereotype.Component;

@Component
public class AlarmknopfKafkaPublisher {

  private AlarmknopfHilferufKafkaGateway alarmknopfHilferufKafkaGateway;
  private AlarmknopfEventFactory alarmknopfEventFactory;

  public AlarmknopfKafkaPublisher(
      AlarmknopfHilferufKafkaGateway alarmknopfHilferufKafkaGateway,
      AlarmknopfEventFactory alarmknopfEventFactory) {
    this.alarmknopfHilferufKafkaGateway = alarmknopfHilferufKafkaGateway;
    this.alarmknopfEventFactory = alarmknopfEventFactory;
  }

  public void publishDeletedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf);
    alarmknopfHilferufKafkaGateway.publishAlarmknopfEvent(event);
  }

  public void publishUpdatedAlarmknopf(Alarmknopf alarmknopf) {
    AlarmknopfEvent event = alarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf);
    alarmknopfHilferufKafkaGateway.publishAlarmknopfEvent(event);
  }
}
