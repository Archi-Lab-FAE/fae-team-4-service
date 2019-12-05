package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackerService {

  private final TrackerRepository trackerRepository;

  public TrackerService(TrackerRepository trackerRepository) {
    this.trackerRepository = trackerRepository;
  }

  public boolean tryToUpdatePositionOfTracker(
      String trackerId, Double newLaengengrad, Double newBreitengrad) {
    Optional<Tracker> foundTracker = trackerRepository.findById(trackerId);

    if (!foundTracker.isPresent()) {
      return false;
    }

    Tracker tracker = foundTracker.get();
    tracker.setPosition(new Position(newLaengengrad, newBreitengrad));

    trackerRepository.save(tracker);
    return true;
  }

  public Tracker createNewTracker(String trackerId) {
    Tracker newTracker = new Tracker(trackerId);
    return trackerRepository.save(newTracker);
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

  // TODO: Refactor isInProximityOfPosition to alarmknopf to avoid passing of radius
  public List<Tracker> getTrackerInProximityOf(
      final Alarmknopf alarmknopf, final double radiusInMeters) {
    return trackerRepository.findAll().stream()
        .filter(
            tracker ->
                tracker.isInProximityOfPosition(
                    alarmknopf.getPosition(), new DistanceInMeters(radiusInMeters)))
        .collect(Collectors.toList());
  }
}
