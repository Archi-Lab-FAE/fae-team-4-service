package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.DemenziellErkrankterDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.KontaktpersonDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.dto.PositionssenderDto;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemenziellErkrankterConsumerTest {

  @Autowired
  private ObjectMapper objectMapper;
  private TrackerRepository mockTrackerRepository;
  private DemenziellErkrankterConsumer demenziellErkrankterConsumer;

  @Before
  public void setUp() {
    this.mockTrackerRepository = Mockito.mock(TrackerRepository.class);
    this.demenziellErkrankterConsumer = new DemenziellErkrankterConsumer(mockTrackerRepository,
        objectMapper);
  }

  @Test
  public void givenKafkaMessage_whenValidCreatedEventWithTracker_thenPersistTracker()
      throws IOException {
    String trackerId = "myTrackerTestId";
    String event = "created";
    DemenziellErkrankterEvent demenziellErkrankterEvent =
        createDemenziellErkrankterEventWithSpecificTrackerIdAndTypeAndZustimmung(trackerId, event,
            true);

    String kafkaMessage = objectMapper.writeValueAsString(demenziellErkrankterEvent);
    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);

    when(mockTrackerRepository.findById(trackerId)).thenReturn(Optional.empty());

    Tracker tracker = new Tracker(trackerId);
    verify(mockTrackerRepository).save(tracker);
  }

  @Test
  public void givenKafkaMessage_whenValidCreatedEventWithOnlyTrackerId_thenPersistTracker()
      throws IOException {
    File file = ResourceUtils
        .getFile("classpath:kafka/KafkaDemenziellErkrankterWithOnlyEssentialFields.json");
    String kafkaMessage = new String(Files.readAllBytes(file.toPath()));

    DemenziellErkrankterEvent demenziellErkrankterEvent = objectMapper.readValue(kafkaMessage,
        DemenziellErkrankterEvent.class);
    String trackerId = demenziellErkrankterEvent.getPayload().getPositionssender().get(0).getId();

    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);

    when(mockTrackerRepository.findById(trackerId)).thenReturn(Optional.empty());

    Tracker tracker = new Tracker(trackerId);
    verify(mockTrackerRepository).save(tracker);
  }

  @Test
  public void givenKafkaMessage_whenValidCreatedEventWithOnlyTrackerIdAlreadyInRepo_thenDontPersistTracker()
      throws IOException {
    File file = ResourceUtils
        .getFile("classpath:kafka/KafkaDemenziellErkrankterWithOnlyEssentialFields.json");
    String kafkaMessage = new String(Files.readAllBytes(file.toPath()));

    DemenziellErkrankterEvent demenziellErkrankterEvent = objectMapper.readValue(kafkaMessage,
        DemenziellErkrankterEvent.class);
    String trackerId = demenziellErkrankterEvent.getPayload().getPositionssender().get(0).getId();

    Tracker tracker = new Tracker(trackerId);
    when(mockTrackerRepository.findById(trackerId)).thenReturn(Optional.of(tracker));

    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);

    verify(mockTrackerRepository, never()).save(tracker);
  }

  @Test
  public void givenKafkaMessage_whenValidEventHasNoZustimmung_thenTryToDeleteTracker()
      throws IOException {
    String trackerId = "myTrackerTestId";
    String event = "created";
    DemenziellErkrankterEvent demenziellErkrankterEvent =
        createDemenziellErkrankterEventWithSpecificTrackerIdAndTypeAndZustimmung(trackerId, event,
            false);

    String kafkaMessage = objectMapper.writeValueAsString(demenziellErkrankterEvent);
    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);

    verify(mockTrackerRepository).deleteById(trackerId);
  }

  @Test
  public void givenKafkaMessage_whenValidDeletedEventWithTracker_thenRemoveTrackerFromRepository()
      throws IOException {
    String trackerId = "myTrackerTestId";
    String event = "deleted";
    DemenziellErkrankterEvent demenziellErkrankterEvent =
        createDemenziellErkrankterEventWithSpecificTrackerIdAndTypeAndZustimmung(trackerId, event,
            true);

    String kafkaMessage = objectMapper.writeValueAsString(demenziellErkrankterEvent);
    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);

    verify(mockTrackerRepository).deleteById(trackerId);
  }

  /*@Test(expected = JsonParseException.class)
  public void givenKafkaMessage_whenNotDeserializableEvent_thenThrowJsonParseException()
      throws IOException {
    String kafkaMessage = "me no valid json";
    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);
  }*/

  @Test(expected = JsonMappingException.class)
  public void givenKafkaMessage_whenEventWithoutTrackerId_thenThrowJsonMappingException()
      throws IOException {
    File file = ResourceUtils
        .getFile("classpath:kafka/KafkaDemenziellErkrankterWithTrackerIdNull.json");
    String kafkaMessage = new String(Files.readAllBytes(file.toPath()));

    demenziellErkrankterConsumer.consumeDemenziellErkrankte(kafkaMessage);
  }

  private DemenziellErkrankterEvent createDemenziellErkrankterEventWithSpecificTrackerIdAndTypeAndZustimmung(
      final String trackerId, final String event, final boolean zustimmung) {
    List<KontaktpersonDto> kontaktpersonDtoList = getKontaktpersonDtoList();

    List<PositionssenderDto> positionssenderDtoList = getPositionssenderDtoList(trackerId);

    DemenziellErkrankterDto demenziellErkrankterDto = getDemenziellErkrankterDto(zustimmung,
        kontaktpersonDtoList, positionssenderDtoList);

    return getDemenziellErkrankterEvent(trackerId, event, demenziellErkrankterDto);
  }

  private DemenziellErkrankterEvent getDemenziellErkrankterEvent(final String trackerId,
      final String event,
      final DemenziellErkrankterDto demenziellErkrankterDto) {
    LocalDateTime localDateEvent = LocalDateTime.of(2020, Month.DECEMBER, 2, 12, 12);
    DemenziellErkrankterEvent demenziellErkrankterEvent = new DemenziellErkrankterEvent();
    demenziellErkrankterEvent.setId("eventId");
    demenziellErkrankterEvent.setKey(trackerId);
    demenziellErkrankterEvent.setVersion(1L);
    demenziellErkrankterEvent.setTimestamp(localDateEvent);
    demenziellErkrankterEvent.setType(event);
    demenziellErkrankterEvent.setPayload(demenziellErkrankterDto);
    return demenziellErkrankterEvent;
  }

  private DemenziellErkrankterDto getDemenziellErkrankterDto(final boolean zustimmung,
      final List<KontaktpersonDto> kontaktpersonDtoList,
      final List<PositionssenderDto> positionssenderDtoList) {
    DemenziellErkrankterDto demenziellErkrankterDto = new DemenziellErkrankterDto();
    demenziellErkrankterDto.setId("payId");
    demenziellErkrankterDto.setName("payName");
    demenziellErkrankterDto.setVorname("payVorname");
    demenziellErkrankterDto.setZustimmung(zustimmung);
    demenziellErkrankterDto.setKontaktpersonen(kontaktpersonDtoList);
    demenziellErkrankterDto.setPositionssender(positionssenderDtoList);
    return demenziellErkrankterDto;
  }

  private List<PositionssenderDto> getPositionssenderDtoList(final String trackerId) {
    Date datePositionssender = new Date(987123987123L);
    PositionssenderDto positionssenderDto = new PositionssenderDto();
    positionssenderDto.setId(trackerId);
    positionssenderDto.setLetzteWartung(datePositionssender);

    List<PositionssenderDto> positionssenderDtoList = new ArrayList<>();
    positionssenderDtoList.add(positionssenderDto);
    return positionssenderDtoList;
  }

  private List<KontaktpersonDto> getKontaktpersonDtoList() {
    KontaktpersonDto kontaktpersonDto = new KontaktpersonDto();
    kontaktpersonDto.setId("kontaktId");
    kontaktpersonDto.setName("kontaktName");
    kontaktpersonDto.setVorname("kontaktVorname");
    kontaktpersonDto.setAktiv(Boolean.TRUE);
    kontaktpersonDto.setTelefonnummer("kontaktTel1234");

    List<KontaktpersonDto> kontaktpersonDtoList = new ArrayList<>();
    kontaktpersonDtoList.add(kontaktpersonDto);
    return kontaktpersonDtoList;
  }
}
