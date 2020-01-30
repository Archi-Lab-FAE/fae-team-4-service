package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfHilferufKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.MessagingServiceClient;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmknopfHilferufService {

  private final AlarmknopfHilferufKafkaPublisher alarmknopfHilferufKafkaPublisher;
  private final MessagingServiceClient messagingServiceClient;

  public AlarmknopfHilferufService(
      final AlarmknopfHilferufKafkaPublisher alarmknopfHilferufKafkaPublisher,
      final MessagingServiceClient messagingServiceClient) {
    this.alarmknopfHilferufKafkaPublisher = alarmknopfHilferufKafkaPublisher;
    this.messagingServiceClient = messagingServiceClient;
  }

  public void sendHilferufeForTracker(
      final List<Tracker> ascertainedTrackerInProximity, final Alarmknopf alarmknopf) {
    List<AlarmknopfHilferuf> createdHilferufe =
        createAlarmknopfHilferufFrom(ascertainedTrackerInProximity);

    publishViaRest(createdHilferufe, alarmknopf);
    publishOnKafka(createdHilferufe);
  }

  private List<AlarmknopfHilferuf> createAlarmknopfHilferufFrom(
      final List<Tracker> ascertainedTrackerInProximity) {
    return ascertainedTrackerInProximity.stream()
        .map(Tracker::createAlarmknopfHilferuf)
        .collect(Collectors.toList());
  }

  private void publishOnKafka(final List<AlarmknopfHilferuf> createdHilferufe) {
    createdHilferufe.forEach(
        alarmknopfHilferufKafkaPublisher::publishAlarmknopfHilferufAusgeloestEvent);
  }

  private void publishViaRest(
      final List<AlarmknopfHilferuf> createdHilferufe, final Alarmknopf alarmknopf) {
    createdHilferufe.forEach(
        alarmknopfHilferuf ->
            messagingServiceClient.alertMessagingSystemAboutAlarmknopfHilferuf(
                alarmknopfHilferuf, alarmknopf));
  }
}
