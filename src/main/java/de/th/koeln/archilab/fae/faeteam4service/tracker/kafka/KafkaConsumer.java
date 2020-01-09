package de.th.koeln.archilab.fae.faeteam4service.tracker.kafka;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

  private final TrackerRepository trackerRepository;

  public KafkaConsumer(TrackerRepository trackerRepository) {
    this.trackerRepository = trackerRepository;
  }

  @KafkaListener(topics = "tracker", autoStartup = "${spring.kafka.enabled}")
  public void consumeTracker(final String message) {
    //TODO persist tracker
    Tracker tracker = new Tracker();
    tracker.setId(message);

    Position position = new Position(3.0, 3.14);
    tracker.setPosition(position);

    trackerRepository.save(tracker);
  }
}
