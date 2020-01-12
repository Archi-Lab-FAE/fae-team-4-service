package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufService;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.util.Optional;

import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmknopfdruckServiceTest {

  private final AlarmknopfdruckService alarmknopfdruckService;
  private final TrackerService trackerService;

  private final AlarmknopfRepository mockAlarmknopfRepository;
  private final TrackerRepository mockTrackerRepository;
  private final AlarmknopfHilferufService mockAlarmknopfHilferufService;


  private static final String ALARMKNOPF_ID = "someId";

  public AlarmknopfdruckServiceTest() {
    this.mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    this.mockTrackerRepository = Mockito
        .mock(TrackerRepository.class);
    this.mockAlarmknopfHilferufService = Mockito.mock(AlarmknopfHilferufService.class);

    trackerService = new TrackerService(
            mockTrackerRepository);
    alarmknopfdruckService = new AlarmknopfdruckService(mockAlarmknopfRepository,
            trackerService, mockAlarmknopfHilferufService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKnopfdruckIsCalledAndHilfeknopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    Alarmknopfdruck alarmknopfdruck = new Alarmknopfdruck(ALARMKNOPF_ID);

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID))
        .thenReturn(Optional.empty());

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

  }
}