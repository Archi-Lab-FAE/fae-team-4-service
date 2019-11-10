package de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.service;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.HilfeknopfId;
import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.HilfeknopfRepository;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class KnopfdruckServiceTest {

  private KnopfdruckService knopfdruckService;
  private HilfeknopfRepository mockHilfeKnopfRepository;

  public KnopfdruckServiceTest(){
    mockHilfeKnopfRepository = Mockito.mock(HilfeknopfRepository.class);
    knopfdruckService = new KnopfdruckService(mockHilfeKnopfRepository);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKNopfdruckIsCalledAndHilfeKNopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    String givenId= "someId";

    when(mockHilfeKnopfRepository.findById(new HilfeknopfId(givenId)))
        .thenReturn(Optional.empty());

    knopfdruckService.handleKnopfdruck(givenId);

  }
}