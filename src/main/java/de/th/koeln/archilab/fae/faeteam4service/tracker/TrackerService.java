package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TrackerService {

  private final TrackerRepository trackerRepository;

  public TrackerService(TrackerRepository trackerRepository) {
    this.trackerRepository = trackerRepository;
  }

  public Tracker updateOrCreateTracker(Tracker tracker) {
    return trackerRepository.save(tracker);
  }

  public boolean deleteTracker(String trackerId) {
    Optional<Tracker> foundTracker = trackerRepository.findById(trackerId);

    if (!foundTracker.isPresent()) {
      return false;
    }

    Tracker tracker = foundTracker.get();
    trackerRepository.delete(tracker);
    return true;
  }

  public List<Tracker> getTrackerInProximityOf(final Alarmknopf alarmknopf) {
    return trackerRepository.findAll().stream()
        .filter(alarmknopf::isTrackerInProximity)
        .collect(Collectors.toList());
  }

  public List<Tracker> getAllTrackers(){
    return trackerRepository.findAll();
  }
}
