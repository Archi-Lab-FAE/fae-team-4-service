package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.TrackerEvent;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.registrierung.DemenziellErkrankterDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.registrierung.PositionssenderDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DemenziellErkrankterConsumer {

  private final TrackerRepository trackerRepository;

  private final ObjectMapper objectMapper;

  public DemenziellErkrankterConsumer(final TrackerRepository trackerRepository,
      final ObjectMapper objectMapper) {
    this.trackerRepository = trackerRepository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "${spring.kafka.consumer.tracker.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
  public void consumeDemenziellErkrankte(@Payload final String message) throws IOException {

    TrackerEvent<DemenziellErkrankterDto> trackerEvent = objectMapper
        .readValue(message, new TypeReference<TrackerEvent<DemenziellErkrankterDto>>() {
        });

    DemenziellErkrankterDto demenziellErkrankterDto = trackerEvent.getPayload();
    String incomingEventType = trackerEvent.getType();
    boolean zustimmung = demenziellErkrankterDto.getZustimmung();
    List<PositionssenderDto> positionssenderDtoList = demenziellErkrankterDto.getPositionssender();

    if (!zustimmung) {
      handleNoZustimmung(positionssenderDtoList);
      return;
    }
    if (incomingEventType.equals(EventType.CREATED.getEventString()) ||
        incomingEventType.equals(EventType.UPDATED.getEventString())) {
      handleCreateOrUpdatedEvent(positionssenderDtoList);
    }
    if (incomingEventType.equals(EventType.DELETED.getEventString())) {
      handleDeleteEvent(positionssenderDtoList);
    }
  }

  private void handleNoZustimmung(final List<PositionssenderDto> positionssenderDtoList) {
    for (PositionssenderDto positionssenderDto : positionssenderDtoList) {
      trackerRepository.deleteById(positionssenderDto.getId());
    }
  }

  private void handleCreateOrUpdatedEvent(final List<PositionssenderDto> positionssenderDtoList) {
    for (PositionssenderDto positionssenderDto : positionssenderDtoList) {
      Tracker tracker = new Tracker(positionssenderDto.getId());
      Optional<Tracker> trackerInRepository = trackerRepository
          .findById(positionssenderDto.getId());

      if (trackerInRepository.isPresent()) {
        continue;
      }
      trackerRepository.save(tracker);
    }
  }

  private void handleDeleteEvent(final List<PositionssenderDto> positionssenderDtoList) {
    for (PositionssenderDto positionssenderDto : positionssenderDtoList) {
      trackerRepository.deleteById(positionssenderDto.getId());
    }
  }
}
