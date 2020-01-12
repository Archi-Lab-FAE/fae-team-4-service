package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfdruckService {

  private final AlarmknopfRepository alarmknopfRepository;
  private final TrackerService trackerService;
  private final AlarmknopfHilferufService alarmknopfHilferufService;

  @Autowired
  public AlarmknopfdruckService(
      final AlarmknopfRepository alarmknopfRepository,
      final TrackerService trackerService,
      final AlarmknopfHilferufService alarmknopfHilferufService) {

    this.alarmknopfRepository = alarmknopfRepository;
    this.trackerService = trackerService;
    this.alarmknopfHilferufService = alarmknopfHilferufService;
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

    List<Tracker> ascertainedTrackerInProximity = getTrackerInProximity(pressedAlarmknopf);
    alarmknopfHilferufService.sendHilferufeForTracker(ascertainedTrackerInProximity);
  }

  private List<Tracker> getTrackerInProximity(final Alarmknopf alarmknopf) {
    return trackerService.getTrackerInProximityOf(alarmknopf);
  }
}
