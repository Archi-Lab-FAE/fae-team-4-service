package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import de.th.koeln.archilab.fae.faeteam4service.Distance;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing.AlarmknopfKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmknopfRegistrierungServiceImplTest {

  private AlarmknopfRepository mockAlarmknopfRepository;
  private AlarmknopfRegistrierungServiceImpl alarmknopfRegistrierungService;
  private List<Alarmknopf> listOfAlarmknoepfeInRepository;
  private Alarmknopf alarmknopf1;
  private AlarmknopfKafkaPublisher mockAlarmknopfKafkaPublisher;

  @Before
  public void setUp() {
    mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    mockAlarmknopfKafkaPublisher = Mockito.mock(AlarmknopfKafkaPublisher.class);

    alarmknopfRegistrierungService =
        new AlarmknopfRegistrierungServiceImpl(
            mockAlarmknopfRepository, mockAlarmknopfKafkaPublisher);

    alarmknopf1 = createTestAlarmknopfWithName("firstId");
    Alarmknopf alarmknopf2 = createTestAlarmknopfWithName("secondId");
    Alarmknopf alarmknopf3 = createTestAlarmknopfWithName("thirdId");
    Alarmknopf alarmknopf4 = createTestAlarmknopfWithName("fourthId");

    listOfAlarmknoepfeInRepository = new ArrayList<>();
    listOfAlarmknoepfeInRepository.add(alarmknopf1);
    listOfAlarmknoepfeInRepository.add(alarmknopf2);
    listOfAlarmknoepfeInRepository.add(alarmknopf3);
    listOfAlarmknoepfeInRepository.add(alarmknopf4);

    when(mockAlarmknopfRepository.findAll()).thenReturn(listOfAlarmknoepfeInRepository);
  }

  @Test
  public void
      givenAlarmknopfRepositoryWithAlarmknoepfen_whenFindAllCall_thenAllAlarmknoepfeShouldBeReturned() {
    when(mockAlarmknopfRepository.findAll()).thenReturn(listOfAlarmknoepfeInRepository);
    List<Alarmknopf> actualReturnedAlarmknoepfe = alarmknopfRegistrierungService.findAll();
    assertThat(actualReturnedAlarmknoepfe, equalTo(listOfAlarmknoepfeInRepository));
  }

  @Test
  public void givenNewAlarmknopfToSave_whenNewAlarmknopfIsSavedSuccessfully_ReturnTrue() {
    Optional<Alarmknopf> alarmknopfOptional = Optional.ofNullable(alarmknopf1);
    assert alarmknopf1 != null;
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId())).thenReturn(alarmknopfOptional);

    boolean isSavedAcutal = alarmknopfRegistrierungService.save(alarmknopf1);

    assertTrue(isSavedAcutal);
  }

  @Test
  public void givenNewAlarmknopfToSave_whenNewAlarmknopfIsSavedWithErrors_ReturnFalse() {
    Optional<Alarmknopf> alarmknopfOptional = Optional.empty();
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId())).thenReturn(alarmknopfOptional);

    boolean isSavedAcutal = alarmknopfRegistrierungService.save(alarmknopf1);

    assertFalse(isSavedAcutal);
  }

  @Test
  public void givenAlarmknopfIdOfSavedAlarmknopf_whenFindAlarmknopfById_ReturnSavedAlarmknopf() {
    String idAlarmknopf1 = alarmknopf1.getId();
    Optional<Alarmknopf> expectedAlarmknopfOptional = Optional.ofNullable(alarmknopf1);
    when(mockAlarmknopfRepository.findById(idAlarmknopf1)).thenReturn(expectedAlarmknopfOptional);

    Optional<Alarmknopf> actualAlarmknopfOptional =
        alarmknopfRegistrierungService.findById(idAlarmknopf1);

    assertThat(actualAlarmknopfOptional, equalTo(expectedAlarmknopfOptional));
  }

  @Test
  public void givenAlarmknopfIdOfNotSavedAlarmknopf_whenFindAlarmknopfById_ReturnSavedAlarmknopf() {
    String alarmknopfIdNotInRepository = "not in repository";
    Optional<Alarmknopf> expectedAlarmknopfOptional = Optional.empty();
    when(mockAlarmknopfRepository.findById(alarmknopfIdNotInRepository))
        .thenReturn(expectedAlarmknopfOptional);

    Optional<Alarmknopf> actualAlarmknopfOptional =
        alarmknopfRegistrierungService.findById(alarmknopfIdNotInRepository);

    assertThat(actualAlarmknopfOptional, equalTo(expectedAlarmknopfOptional));
  }

  @Test
  public void givenAlarmknopfIdOfSavedAlarmknopf_whenDeleteAlarmknopfById_ReturnTrue() {
    String idAlarmknopf1 = alarmknopf1.getId();
    Optional<Alarmknopf> expectedAlarmknopfOptional = Optional.ofNullable(alarmknopf1);

    when(mockAlarmknopfRepository.findById(idAlarmknopf1)).thenReturn(expectedAlarmknopfOptional);

    boolean isDeletedActual = alarmknopfRegistrierungService.deleteById(idAlarmknopf1);

    assertTrue(isDeletedActual);
  }

  @Test
  public void givenAlarmknopfIdOfNotSavedAlarmknopf_whenDeleteAlarmknopfById_ReturnFalse() {
    String alarmknopfIdNotInRepository = "not in repository";
    Optional<Alarmknopf> expectedAlarmknopfOptional = Optional.empty();
    when(mockAlarmknopfRepository.findById(alarmknopfIdNotInRepository))
        .thenReturn(expectedAlarmknopfOptional);

    boolean isDeletedActual =
        alarmknopfRegistrierungService.deleteById(alarmknopfIdNotInRepository);

    assertFalse(isDeletedActual);
  }

  @Test
  public void givenAlarmknopfIsSuccesfullySaved_whenSaveAlarmknopf_ThenUpdatedEventShouldBeSent() {
    when(mockAlarmknopfRepository.save(alarmknopf1)).thenReturn(alarmknopf1);
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId()))
        .thenReturn(Optional.of(alarmknopf1));

    alarmknopfRegistrierungService.save(alarmknopf1);

    verify(mockAlarmknopfKafkaPublisher).publishUpdatedAlarmknopf(alarmknopf1);
  }

  @Test
  public void
      givenAlarmknopfIsNotSuccesfullySaved_whenSaveAlarmknopf_ThenUpdatedEventShouldNotBeSent() {
    when(mockAlarmknopfRepository.save(alarmknopf1)).thenReturn(alarmknopf1);
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId())).thenReturn(Optional.empty());

    alarmknopfRegistrierungService.save(alarmknopf1);

    verify(mockAlarmknopfKafkaPublisher, never()).publishUpdatedAlarmknopf(alarmknopf1);
  }

  @Test
  public void
      givenAlarmknopfIsSuccesfullyDeleted_whenDeleteAlarmknopf_ThenDeleteEventShouldBeSent() {
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId()))
        .thenReturn(Optional.of(alarmknopf1));

    alarmknopfRegistrierungService.deleteById(alarmknopf1.getId());

    verify(mockAlarmknopfKafkaPublisher).publishDeletedAlarmknopf(alarmknopf1);
  }

  @Test
  public void
      givenAlarmknopfIsNotSuccesfullyDeleted_whenDeleteAlarmknopf_ThenDeleteEventShouldNotBeSent() {
    when(mockAlarmknopfRepository.findById(alarmknopf1.getId())).thenReturn(Optional.empty());

    alarmknopfRegistrierungService.deleteById(alarmknopf1.getId());

    verify(mockAlarmknopfKafkaPublisher, never()).publishDeletedAlarmknopf(alarmknopf1);
  }

  private Alarmknopf createTestAlarmknopfWithName(final String id) {
    return new Alarmknopf(id, "myName", new Position(), new Distance());
  }
}
