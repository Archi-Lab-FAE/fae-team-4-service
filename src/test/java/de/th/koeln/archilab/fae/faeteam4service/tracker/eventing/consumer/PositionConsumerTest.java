package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.consumer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.TrackerEvent;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.ortung.CurrentPositionDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.ortung.OrtungTrackerDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PositionConsumerTest {

  @Autowired
  private ObjectMapper objectMapper;
  private TrackerRepository mockTrackerRepository;
  private PositionConsumer positionConsumer;

  @Before
  public void setUp() {
    this.mockTrackerRepository = Mockito.mock(TrackerRepository.class);
    this.positionConsumer = new PositionConsumer(mockTrackerRepository,
        objectMapper);
  }

  @Test
  public void givenKafkaMessage_whenValidEventWithTrackerAndPosition_thenPersist()
      throws IOException {
    double breitengrad = 32.11;
    double laengengrad = 13.44;
    String trackerId = "myTrackerId";

    TrackerEvent<OrtungTrackerDto> trackerEvent = createTrackerEvent(trackerId, breitengrad,
        laengengrad);
    String kafkaMessage = objectMapper.writeValueAsString(trackerEvent);
    Tracker expectedTracker = createTracker(trackerId, breitengrad, laengengrad);

    when(mockTrackerRepository.findById(trackerId)).thenReturn(Optional.of(expectedTracker));
    positionConsumer.consumeTracker(kafkaMessage);

    ArgumentCaptor<Tracker> argument = ArgumentCaptor.forClass(Tracker.class);
    verify(mockTrackerRepository).save(argument.capture());

    Tracker actualTracker = argument.getValue();
    compareTracker(expectedTracker, actualTracker);
  }

  @Test
  public void givenKafkaMessage_whenValidEventWithTrackerIdNotInRepo_thenDontPersistTracker()
      throws IOException {
    double breitengrad = 32.11;
    double laengengrad = 13.44;
    String trackerId = "myTrackerId";

    TrackerEvent<OrtungTrackerDto> trackerEvent = createTrackerEvent(trackerId, breitengrad,
        laengengrad);
    String kafkaMessage = objectMapper.writeValueAsString(trackerEvent);

    when(mockTrackerRepository.findById(trackerId)).thenReturn(Optional.empty());
    positionConsumer.consumeTracker(kafkaMessage);

    verify(mockTrackerRepository, never()).save(any());
  }

  @Test(expected = JsonParseException.class)
  public void givenKafkaMessage_whenNotDeserializableEvent_thenThrowJsonParseException()
      throws IOException {
    String kafkaMessage = "me no valid json";
    positionConsumer.consumeTracker(kafkaMessage);
  }

  @Test(expected = JsonMappingException.class)
  public void givenKafkaMessage_whenEventWithoutTrackerId_thenThrowJsonMappingException()
      throws IOException {
    File file = ResourceUtils
        .getFile("classpath:kafka/KafkaOrtungTrackerWithTrackerIdNull.json");
    String kafkaMessage = new String(Files.readAllBytes(file.toPath()));

    positionConsumer.consumeTracker(kafkaMessage);
  }

  private void compareTracker(Tracker expectedTracker, Tracker actualTracker) {
    assert (expectedTracker.getId().equals(actualTracker.getId()));
    assert expectedTracker.getPosition() != null;
    assert expectedTracker.getPosition().getBreitengrad() != null;
    double expectedBreitengrad = expectedTracker.getPosition().getBreitengrad()
        .getBreitengradDezimal();
    assert expectedTracker.getPosition().getLaengengrad() != null;
    double expectedLaengengrad = expectedTracker.getPosition().getLaengengrad()
        .getLaengengradDezimal();
    assert actualTracker.getPosition() != null;
    assert actualTracker.getPosition().getBreitengrad() != null;
    double actualBreitengrad = actualTracker.getPosition().getBreitengrad()
        .getBreitengradDezimal();
    assert actualTracker.getPosition().getLaengengrad() != null;
    double actualLaengengrad = actualTracker.getPosition().getLaengengrad()
        .getLaengengradDezimal();
    assertThat(expectedBreitengrad, is(equalTo((actualBreitengrad))));
    assertThat(expectedLaengengrad, is(equalTo((actualLaengengrad))));
  }

  private Tracker createTracker(String trackerId, double breitengrad, double laengengrad) {
    Position position = new Position(breitengrad, laengengrad);
    Tracker tracker = new Tracker(trackerId);
    tracker.setPosition(position);
    return tracker;
  }

  private TrackerEvent<OrtungTrackerDto> createTrackerEvent(final String trackerId,
      final double breitengrad, final double laengengrad) {
    CurrentPositionDto currentPositionDto = createCurrentPositionDto(breitengrad, laengengrad);
    OrtungTrackerDto ortungTrackerDto = createOrtungTrackerDto(trackerId, currentPositionDto);

    return createOrtungTrackerDtoTrackerEvent(ortungTrackerDto);
  }

  private TrackerEvent<OrtungTrackerDto> createOrtungTrackerDtoTrackerEvent(
      final OrtungTrackerDto ortungTrackerDto) {
    LocalDateTime localDateEvent = LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 12);
    TrackerEvent<OrtungTrackerDto> trackerEvent = new TrackerEvent<>();
    trackerEvent.setId("eventId");
    trackerEvent.setKey("myKey");
    trackerEvent.setVersion(1L);
    trackerEvent.setTimestamp(localDateEvent);
    trackerEvent.setType("myType");
    trackerEvent.setPayload(ortungTrackerDto);
    return trackerEvent;
  }

  private OrtungTrackerDto createOrtungTrackerDto(final String trackerId,
      final CurrentPositionDto currentPositionDto) {
    OrtungTrackerDto ortungTrackerDto = new OrtungTrackerDto();
    ortungTrackerDto.setTrackerId(trackerId);
    ortungTrackerDto.setCurrentPosition(currentPositionDto);
    return ortungTrackerDto;
  }

  private CurrentPositionDto createCurrentPositionDto(final double breitengrad,
      final double laengengrad) {
    CurrentPositionDto currentPositionDto = new CurrentPositionDto();
    currentPositionDto.setLatitude(breitengrad);
    currentPositionDto.setLongitude(laengengrad);
    return currentPositionDto;
  }
}
