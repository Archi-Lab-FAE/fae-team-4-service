package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerRepository;
import java.util.Optional;

import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

public class AlarmknopfdruckServiceTest {

  private final AlarmknopfdruckService alarmknopfdruckService;
  private final AlarmknopfRepository mockAlarmknopfRepository;

  private final TrackerRepository mockTrackerRepository;
  private final TrackerService trackerService;

  private final KafkaTemplate mockKafkaTemplate;

  private static final String ALARMKNOPF_ID = "someId";

  public AlarmknopfdruckServiceTest() {
    mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    mockTrackerRepository = Mockito
        .mock(TrackerRepository.class);
    mockKafkaTemplate = Mockito.mock(KafkaTemplate.class);

    trackerService = new TrackerService(
            mockTrackerRepository);
    alarmknopfdruckService = new AlarmknopfdruckService(mockAlarmknopfRepository,
            trackerService, mockKafkaTemplate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKnopfdruckIsCalledAndHilfeknopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    Alarmknopfdruck alarmknopfdruck = new Alarmknopfdruck(ALARMKNOPF_ID);

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID))
        .thenReturn(Optional.empty());

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

  }
}