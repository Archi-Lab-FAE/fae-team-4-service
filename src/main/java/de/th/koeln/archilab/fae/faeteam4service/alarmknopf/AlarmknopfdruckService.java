package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final TrackerService trackerService;
  private final KafkaTemplate<String, List> kafkaTemplate;

  private static final String KAFKA_TOPIC = "Alarmmeldung";

  public AlarmknopfdruckService(
      final AlarmknopfRepository alarmknopfRepository,
      final TrackerService trackerService,
      final KafkaTemplate<String, List> kafkaTemplate) {

    this.alarmknopfRepository = alarmknopfRepository;
    this.trackerService = trackerService;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Handles a Knopfdruck and created Hilferufe if there are DementiellErkranktePersonen nearby the
   * Alarmknopf.
   *
   * @param alarmknopfdruck alarmknopfdruck
   */
  public void handleAlarmknopfdruck(final Alarmknopfdruck alarmknopfdruck) {
    Alarmknopf pressedAlarmknopf =
        alarmknopfRepository
            .findById(alarmknopfdruck.getAlarmknopfId())
            .orElseThrow(() -> new IllegalArgumentException("Alarmknopf not found"));

    List<Tracker> potentiellBetroffeneDementiellErkranktePersonen =
        getPersonenInKnopfProximity(pressedAlarmknopf);

    List createdHilferufe =
        potentiellBetroffeneDementiellErkranktePersonen.stream()
            .map(Tracker::createAlarmknopfHilferuf)
            .collect(Collectors.toList());

    kafkaTemplate.send(KAFKA_TOPIC, createdHilferufe);
  }

  private List<Tracker> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
    double radiusInMeters = 5;
    return trackerService.getTrackerInProximityOf(alarmknopf, radiusInMeters);
  }
}
