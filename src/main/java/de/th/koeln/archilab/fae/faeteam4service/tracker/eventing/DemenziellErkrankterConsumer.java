package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.PositionssenderDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DemenziellErkrankterConsumer {

  private final TrackerRepository trackerRepository;

  private final ObjectMapper objectMapper;

  private final ErrorService errorService;

  private enum Type {CREATED, UPDATED, DELETED}

  public DemenziellErkrankterConsumer(final TrackerRepository trackerRepository,
      final ObjectMapper objectMapper, final ErrorService errorService) {
    this.trackerRepository = trackerRepository;
    this.objectMapper = objectMapper;
    this.errorService = errorService;
  }

  @KafkaListener(topics = "${spring.kafka.consumer.tracker.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
  public void consumeDemenziellErkrankte(final String message) {
    errorService.persistString("Anfang der Methode");
    try {
      DemenziellErkrankterEvent demenziellErkrankterEvent = objectMapper.readValue(message,
          DemenziellErkrankterEvent.class);

      errorService.persistString("message: " + message);
      errorService.persistString("deserialisiert");
      String eventType = demenziellErkrankterEvent.getType().toUpperCase();
      errorService.persistString("eventType: " + eventType);
      boolean zustimmung = demenziellErkrankterEvent.getPayload().getZustimmung();
      List<PositionssenderDto> positionssenderDtoList =
          demenziellErkrankterEvent.getPayload().getPositionssender();

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
    } catch (Exception e) {
      errorService.persistException(e);
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
