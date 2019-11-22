package de.th.koeln.archilab.fae.faeteam4service.service;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.DementiellErkranktePersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePersonRepository;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmknopfdruckServiceTest {

  private KnopfdruckService knopfdruckService;
  private AlarmknopfRepository mockAlarmknopfRepository;
  private DementiellErkranktePersonRepository mockDementiellErkranktePersonRepository;
  private DementiellErkranktePersonenService dementiellErkranktePersonenService;

  public AlarmknopfdruckServiceTest(){
    mockAlarmknopfRepository = Mockito.mock(AlarmknopfRepository.class);
    mockDementiellErkranktePersonRepository = Mockito.mock(DementiellErkranktePersonRepository.class);

    dementiellErkranktePersonenService = new DementiellErkranktePersonenService(mockDementiellErkranktePersonRepository);
    knopfdruckService = new KnopfdruckService(mockAlarmknopfRepository, dementiellErkranktePersonenService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKNopfdruckIsCalledAndHilfeKNopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    String givenId= "someId";

    when(mockAlarmknopfRepository.findById(givenId))
        .thenReturn(Optional.empty());

    knopfdruckService.handleAlarmknopfdruck(givenId);

  }
}