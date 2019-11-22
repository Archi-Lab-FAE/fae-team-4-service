package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;

  public AlarmknopfdruckService(final AlarmknopfRepository alarmknopfRepository,
      final DementiellErkranktePersonenService dementiellErkranktePersonenService) {
    this.alarmknopfRepository = alarmknopfRepository;
    this.dementiellErkranktePersonenService = dementiellErkranktePersonenService;
  }

  /**
   * Handles a Knopfdruck and created Hilferufe if there are DementiellErkranktePersonen nearby the
   * Alarmknopf.
   *
   * @param alarmknopfdruck alarmknopfdruck
   */
  void handleAlarmknopfdruck(final Alarmknopfdruck alarmknopfdruck) {
    Alarmknopf pressedAlarmknopf = alarmknopfRepository.findById(alarmknopfdruck.getAlarmknopfId())
        .orElseThrow(() -> new IllegalArgumentException("HilfeKnopf not found"));

    List<DementiellErkranktePerson> potentiellBetroffeneDementiellErkranktePersonen =
        getPersonenInKnopfProximity(pressedAlarmknopf);

    List createdHilferufe = potentiellBetroffeneDementiellErkranktePersonen.stream()
        .map(DementiellErkranktePerson::createAlarmknopfHilferuf)
        .collect(Collectors.toList());

    //TODO: publish createdHilferufe here
  }


  private List<DementiellErkranktePerson> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
    double radiusInMeters = 5;
    return dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radiusInMeters);
  }
}