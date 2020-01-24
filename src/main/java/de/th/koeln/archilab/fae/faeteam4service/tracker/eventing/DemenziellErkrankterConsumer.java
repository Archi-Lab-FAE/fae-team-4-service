package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemenziellErkrankterConsumer {

  private final TrackerRepository trackerRepository;

  private final ObjectMapper objectMapper;

  private enum Type {CREATED, DELETED}

  public DemenziellErkrankterConsumer(final TrackerRepository trackerRepository,
      final ObjectMapper objectMapper) {
    this.trackerRepository = trackerRepository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "demenziellErkrankte", autoStartup = "${spring.kafka.enabled}")
  public void consumeDemenziellErkrankte(final String message) throws IOException {

    DemenziellErkrankterEvent demenziellErkrankterEvent = objectMapper.readValue(message,
        DemenziellErkrankterEvent.class);

    String eventType = demenziellErkrankterEvent.getType().toUpperCase();
    boolean zustimmung = demenziellErkrankterEvent.getPayload().getZustimmung();

    if (!zustimmung) {
      handleNoZustimmung(demenziellErkrankterEvent.getKey());
      return;
    }
    if (eventType.equals(Type.CREATED.toString())) {
      handleCreateEvent(demenziellErkrankterEvent.getKey());
    }
    if (eventType.equals(Type.DELETED.toString())) {
      handleDeleteEvent(demenziellErkrankterEvent.getKey());
    }
  }

  private void handleNoZustimmung(final String trackerId) {
    trackerRepository.deleteById(trackerId);
  }

  private void handleCreateEvent(final String trackerId) {
    Tracker tracker = new Tracker();
    tracker.setId(trackerId);
    Optional<Tracker> trackerInRepository = trackerRepository.findById(trackerId);

    if (trackerInRepository.isPresent()) {
      return;
    }
    trackerRepository.save(tracker);
  }

  private void handleDeleteEvent(final String trackerId) {
    trackerRepository.deleteById(trackerId);
  }
}
