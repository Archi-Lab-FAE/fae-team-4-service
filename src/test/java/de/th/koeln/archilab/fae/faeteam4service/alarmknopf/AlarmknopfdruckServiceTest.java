package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufService;
import de.th.koeln.archilab.fae.faeteam4service.common.Distance;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmknopfdruckServiceTest {

  private final AlarmknopfdruckService alarmknopfdruckService;
  private final TrackerService mockTrackerService;

  private final AlarmknopfRepository mockAlarmknopfRepository;
  private final AlarmknopfHilferufService mockAlarmknopfHilferufService;


  private static final String ALARMKNOPF_ID = "someId";
  private static final String ALARMKNOPF_NAME = "myName";

  public AlarmknopfdruckServiceTest() {
    this.mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    this.mockAlarmknopfHilferufService = Mockito.mock(AlarmknopfHilferufService.class);
    this.mockTrackerService = Mockito.mock(TrackerService.class);

    alarmknopfdruckService = new AlarmknopfdruckService(mockAlarmknopfRepository,
        mockTrackerService, mockAlarmknopfHilferufService);
  }

  @Test
  public void givenHandleKnopfdruckIsCalled_whenKnopfdruckIsValid_thenCollectAscertainedTrackerAndCallHilferufService() {
    Alarmknopfdruck alarmknopfdruck = new Alarmknopfdruck(ALARMKNOPF_ID);
    Alarmknopf alarmknopf = new Alarmknopf(ALARMKNOPF_ID, ALARMKNOPF_NAME, new Position(),
        new Distance());
    List<Tracker> trackerList = new ArrayList<>();

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID)).thenReturn(Optional.of(alarmknopf));
    when(mockTrackerService.getTrackerInProximityOf(alarmknopf)).thenReturn(trackerList);

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

    verify(mockAlarmknopfHilferufService).sendHilferufeForTracker(trackerList, alarmknopf);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKnopfdruckIsCalledAndHilfeknopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    Alarmknopfdruck alarmknopfdruck = new Alarmknopfdruck(ALARMKNOPF_ID);

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID)).thenReturn(Optional.empty());

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

  }
}