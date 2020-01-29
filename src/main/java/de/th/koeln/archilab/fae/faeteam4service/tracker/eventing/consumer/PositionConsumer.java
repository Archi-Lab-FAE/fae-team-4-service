package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.TrackerEvent;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.ortung.OrtungTrackerDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PositionConsumer {

  private final TrackerRepository trackerRepository;

  private final ObjectMapper objectMapper;

  public PositionConsumer(final TrackerRepository trackerRepository,
      final ObjectMapper objectMapper) {
    this.trackerRepository = trackerRepository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "${spring.kafka.consumer.position.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
  public void consumeTracker(final String message) throws IOException {
    TrackerEvent<OrtungTrackerDto> trackerEvent = objectMapper.readValue(message,
        new TypeReference<TrackerEvent<OrtungTrackerDto>>() {
        });
    OrtungTrackerDto ortungTrackerDto = trackerEvent.getPayload();

    Tracker incomingTracker = getTracker(ortungTrackerDto);
    Optional<Tracker> trackerInRepository = trackerRepository
        .findById(incomingTracker.getId());

    trackerInRepository.ifPresent(tracker -> mergeAndPersistTracker(incomingTracker, tracker));
  }

  private void mergeAndPersistTracker(Tracker incomingTracker, Tracker trackerInRepository) {
    Position incomingTrackerPosition = incomingTracker.getPosition();
    trackerInRepository.setPosition(incomingTrackerPosition);
    trackerRepository.save(trackerInRepository);
  }

  private Tracker getTracker(OrtungTrackerDto ortungTrackerDto) {
    double breitengrad = ortungTrackerDto.getCurrentPosition().getLatitude();
    double langengrad = ortungTrackerDto.getCurrentPosition().getLongitude();
    Position position = new Position(breitengrad, langengrad);

    String trackerId = ortungTrackerDto.getTrackerId();
    Tracker tracker = new Tracker(trackerId);
    tracker.setPosition(position);
    return tracker;
  }
}
