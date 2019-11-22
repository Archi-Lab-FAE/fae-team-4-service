package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.DementiellErkranktePersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import org.springframework.stereotype.Service;

@Service
public class KnopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;

  public KnopfdruckService(final AlarmknopfRepository alarmknopfRepository,
      final DementiellErkranktePersonenService dementiellErkranktePersonenService) {
    this.alarmknopfRepository = alarmknopfRepository;
    this.dementiellErkranktePersonenService = dementiellErkranktePersonenService;
  }

  /**
   * Gets Knopf by id from Repository.
   * @param hilfeKnopfIdString KnopfId
   */
  void handleKnopfdruck(final String hilfeKnopfIdString) {
    Alarmknopf pressedAlarmknopf = alarmknopfRepository.findById(hilfeKnopfIdString)
        .orElseThrow(() -> new IllegalArgumentException("HilfeKnopf not found"));

  }


  private Iterable<DementiellErkranktePerson> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
    double radiusInMeters = 5;
    return dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radiusInMeters);
  }
}
