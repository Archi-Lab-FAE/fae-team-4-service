package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing.KafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfHilferufService {

  private final KafkaPublisher kafkaPublisher;

  @Autowired
  public AlarmknopfHilferufService(final KafkaPublisher kafkaPublisher) {
    this.kafkaPublisher = kafkaPublisher;
  }

  public void sendHilferufeForTracker(final List<Tracker> ascertainedTrackerInProximity) {
    List<AlarmknopfHilferuf> createdHilferufe = getAlarmknopfHilferufeList(
        ascertainedTrackerInProximity);

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
      kafkaPublisher.publishAlarmknopfHilferufAusgeloestEvent(alarmknopfHilferuf);
    }
  }
}
