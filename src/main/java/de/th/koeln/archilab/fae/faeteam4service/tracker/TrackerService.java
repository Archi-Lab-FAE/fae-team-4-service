package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.th.koeln.archilab.fae.faeteam4service.position.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.springframework.stereotype.Service;

@Service
public class TrackerService {

  private final TrackerRepository trackerRepository;

  public TrackerService(TrackerRepository trackerRepository) {
    this.trackerRepository = trackerRepository;
  }

  public void handleNewTrackerPosition(
      String trackerId, Double newLaengengrad, Double newBreitengrad) {
    Position position = new Position(newLaengengrad, newBreitengrad);
    Tracker tracker =
        trackerRepository.findById(trackerId).orElse(new Tracker(trackerId, position));
    tracker.setPosition(position);

    trackerRepository.save(tracker);
  }

  public List<Tracker> getTrackerInProximityOf(
      final Alarmknopf alarmknopf, final double radiusInMeters) {
    Iterable<Tracker> allTrackers = trackerRepository.findAll();
    return StreamSupport.stream(allTrackers.spliterator(), false)
        .filter(
            tracker ->
                tracker.isInProximityOfPosition(
                    alarmknopf.getPosition(), new DistanceInMeters(radiusInMeters)))
        .collect(Collectors.toList());
  }
}
