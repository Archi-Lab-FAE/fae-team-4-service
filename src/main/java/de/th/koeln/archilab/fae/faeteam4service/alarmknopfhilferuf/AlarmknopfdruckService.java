package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing.KafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final TrackerService trackerService;
  private final KafkaPublisher kafkaPublisher;

  @Autowired
  public AlarmknopfdruckService(
      final AlarmknopfRepository alarmknopfRepository,
      final TrackerService trackerService,
      final KafkaPublisher kafkaPublisher) {

    this.alarmknopfRepository = alarmknopfRepository;
    this.trackerService = trackerService;
    this.kafkaPublisher = kafkaPublisher;
  }

  /**
   * Handles a Knopfdruck and created Hilferufe if there are DementiellErkranktePersonen nearby the
   * Alarmknopf.
   *
   * @param alarmknopfdruck alarmknopfdruck
   */
  public void handleAlarmknopfdruck(final Alarmknopfdruck alarmknopfdruck) {
    List<AlarmknopfHilferuf> createdHilferufe = getHilferufeList(alarmknopfdruck);

    publishOnKafka(createdHilferufe);
  }

  private List<AlarmknopfHilferuf> getHilferufeList(Alarmknopfdruck alarmknopfdruck) {
    Alarmknopf pressedAlarmknopf = alarmknopfRepository.findById(alarmknopfdruck.getAlarmknopfId())
        .orElseThrow(() -> new IllegalArgumentException("Alarmknopf not found"));

    List<Tracker> potentiellBetroffeneDementiellErkranktePersonen =
        getPersonenInKnopfProximity(pressedAlarmknopf);

    return getAlarmknopfHilferufeList(
        potentiellBetroffeneDementiellErkranktePersonen);
  }

  private List<AlarmknopfHilferuf> getAlarmknopfHilferufeList(
      List<Tracker> potentiellBetroffeneDementiellErkranktePersonen) {
    return potentiellBetroffeneDementiellErkranktePersonen.stream()
        .map(Tracker::createAlarmknopfHilferuf)
        .collect(Collectors.toList());
  }

  private void publishOnKafka(List<AlarmknopfHilferuf> createdHilferufe) {
    for (AlarmknopfHilferuf alarmknopfHilferuf : createdHilferufe) {
      kafkaPublisher.publishAlarmknopfHilferufAusgeloestEvent(alarmknopfHilferuf);
    }
  }

  private List<Tracker> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
    return trackerService.getTrackerInProximityOf(alarmknopf);
  }
}
