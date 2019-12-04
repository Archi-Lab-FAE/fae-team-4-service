package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePerson;
import java.util.List;
import java.util.stream.Collectors;

import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonenService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;
  private final KafkaTemplate<String, List> kafkaTemplate;

  private static final String KAFKA_TOPIC = "Alarmmeldung";

  public AlarmknopfdruckService(final AlarmknopfRepository alarmknopfRepository,
      final DementiellErkranktePersonenService dementiellErkranktePersonenService,
      final KafkaTemplate<String, List> kafkaTemplate) {

    this.alarmknopfRepository = alarmknopfRepository;
    this.dementiellErkranktePersonenService = dementiellErkranktePersonenService;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Handles a Knopfdruck and created Hilferufe if there are DementiellErkranktePersonen nearby the
   * Alarmknopf.
   *
   * @param alarmknopfdruck alarmknopfdruck
   */
  public void handleAlarmknopfdruck(final Alarmknopfdruck alarmknopfdruck) {
    Alarmknopf pressedAlarmknopf = alarmknopfRepository.findById(alarmknopfdruck.getAlarmknopfId())
        .orElseThrow(() -> new IllegalArgumentException("Alarmknopf not found"));

    List<DementiellErkranktePerson> potentiellBetroffeneDementiellErkranktePersonen =
        getPersonenInKnopfProximity(pressedAlarmknopf);

    List createdHilferufe = potentiellBetroffeneDementiellErkranktePersonen.stream()
        .map(DementiellErkranktePerson::createAlarmknopfHilferuf)
        .collect(Collectors.toList());

    kafkaTemplate.send(KAFKA_TOPIC, createdHilferufe);
  }


  private List<DementiellErkranktePerson> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
    double radiusInMeters = 5;
    return dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radiusInMeters);
  }
}
