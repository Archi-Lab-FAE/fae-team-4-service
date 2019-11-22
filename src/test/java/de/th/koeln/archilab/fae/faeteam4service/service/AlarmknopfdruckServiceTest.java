package de.th.koeln.archilab.fae.faeteam4service.service;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.DementiellErkranktePersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePersonRepository;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmknopfdruckServiceTest {

  private AlarmknopfdruckService alarmknopfdruckService;
  private AlarmknopfRepository mockAlarmknopfRepository;
  private DementiellErkranktePersonRepository mockDementiellErkranktePersonRepository;
  private DementiellErkranktePersonenService dementiellErkranktePersonenService;

  private static final String ALARMKNOPF_ID= "someId";

  public AlarmknopfdruckServiceTest(){
    mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    mockDementiellErkranktePersonRepository = Mockito.mock(DementiellErkranktePersonRepository.class);

    dementiellErkranktePersonenService = new DementiellErkranktePersonenService(mockDementiellErkranktePersonRepository);
    alarmknopfdruckService = new AlarmknopfdruckService(mockAlarmknopfRepository, dementiellErkranktePersonenService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKnopfdruckIsCalledAndHilfeknopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    Alarmknopfdruck alarmknopfdruck= new Alarmknopfdruck(ALARMKNOPF_ID);

    when(mockAlarmknopfRepository.findById(ALARMKNOPF_ID))
        .thenReturn(Optional.empty());

    alarmknopfdruckService.handleAlarmknopfdruck(alarmknopfdruck);

  }
}