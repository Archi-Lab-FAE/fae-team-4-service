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

  private enum Type {CREATED, UPDATED, DELETED}

  public DemenziellErkrankterConsumer(final TrackerRepository trackerRepository,
      final ObjectMapper objectMapper) {
    this.trackerRepository = trackerRepository;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "${spring.kafka.consumer.tracker.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
  public void consumeDemenziellErkrankte(@Payload final String message) throws IOException {

    String myMessage = removeLineFeedCharacters(message);

    TrackerEvent<DemenziellErkrankterDto> trackerEvent = objectMapper
        .readValue(myMessage, new TypeReference<TrackerEvent<DemenziellErkrankterDto>>() {
        });

    DemenziellErkrankterDto demenziellErkrankterDto = trackerEvent.getPayload();
    String eventType = trackerEvent.getType().toUpperCase();
    boolean zustimmung = demenziellErkrankterDto.getZustimmung();
    List<PositionssenderDto> positionssenderDtoList = demenziellErkrankterDto.getPositionssender();

    if (!zustimmung) {
      handleNoZustimmung(positionssenderDtoList);
      return;
    }
    if (eventType.equals(Type.CREATED.toString()) || eventType.equals(Type.UPDATED.toString())) {
      handleCreateOrUpdatedEvent(positionssenderDtoList);
    }
    if (eventType.equals(Type.DELETED.toString())) {
      handleDeleteEvent(positionssenderDtoList);
    }
  }

  private String removeLineFeedCharacters(final String message) {
    String sequenceToRemove = "\\n";
    if (message.contains(sequenceToRemove)) {
      String myMessage = message.substring(1, message.length() - 1);
      return myMessage.replace(sequenceToRemove, "").replace("\\", "");
    }
    return message;
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
