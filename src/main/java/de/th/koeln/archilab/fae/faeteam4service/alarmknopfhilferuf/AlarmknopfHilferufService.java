package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfHilferufKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.AlarmknopfHilferufAlerter;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfHilferufService {

  private final AlarmknopfHilferufKafkaPublisher alarmknopfHilferufKafkaPublisher;
  private final AlarmknopfHilferufAlerter alarmknopfHilferufAlerter;

  @Autowired
  public AlarmknopfHilferufService(
      final AlarmknopfHilferufKafkaPublisher alarmknopfHilferufKafkaPublisher,
      final AlarmknopfHilferufAlerter alarmknopfHilferufAlerter) {
    this.alarmknopfHilferufKafkaPublisher = alarmknopfHilferufKafkaPublisher;
    this.alarmknopfHilferufAlerter = alarmknopfHilferufAlerter;
  }

  public void sendHilferufeForTracker(final List<Tracker> ascertainedTrackerInProximity) {
    List<AlarmknopfHilferuf> createdHilferufe =
        getAlarmknopfHilferufeList(ascertainedTrackerInProximity);

    publishViaRest(createdHilferufe);
    publishOnKafka(createdHilferufe);
  }

  private List<AlarmknopfHilferuf> getAlarmknopfHilferufeList(
      final List<Tracker> ascertainedTrackerInProximity) {
    return ascertainedTrackerInProximity.stream()
        .map(Tracker::createAlarmknopfHilferuf)
        .collect(Collectors.toList());
  }

  private void publishOnKafka(final List<AlarmknopfHilferuf> createdHilferufe) {
    for (AlarmknopfHilferuf alarmknopfHilferuf : createdHilferufe) {
      alarmknopfHilferufKafkaPublisher.publishAlarmknopfHilferufAusgeloestEvent(alarmknopfHilferuf);
    }
  }

  private void publishViaRest(final List<AlarmknopfHilferuf> createdHilferufe) {
    createdHilferufe
        .forEach(alarmknopfHilferufAlerter::alertMessagingSystemAboutAlarmknopfHilferuf);
  }
}
