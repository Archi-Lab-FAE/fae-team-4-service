package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfdruckService;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonRepository;
import java.util.Optional;

import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonenService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

public class AlarmknopfdruckServiceTest {

  private final AlarmknopfdruckService alarmknopfdruckService;
  private final AlarmknopfRepository mockAlarmknopfRepository;

  private final DementiellErkranktePersonRepository mockDementiellErkranktePersonRepository;
  private final DementiellErkranktePersonenService dementiellErkranktePersonenService;

  private final KafkaTemplate mockKafkaTemplate;

  private static final String ALARMKNOPF_ID = "someId";

  public AlarmknopfdruckServiceTest() {
    mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    mockDementiellErkranktePersonRepository = Mockito
        .mock(DementiellErkranktePersonRepository.class);
    mockKafkaTemplate = Mockito.mock(KafkaTemplate.class);

    dementiellErkranktePersonenService = new DementiellErkranktePersonenService(
        mockDementiellErkranktePersonRepository);
    alarmknopfdruckService = new AlarmknopfdruckService(mockAlarmknopfRepository,
        dementiellErkranktePersonenService, mockKafkaTemplate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKnopfdruckIsCalledAndHilfeknopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    Alarmknopfdruck alarmknopfdruck = new Alarmknopfdruck(ALARMKNOPF_ID);

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID))
        .thenReturn(Optional.empty());

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

  }
}