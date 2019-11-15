package de.th.koeln.archilab.fae.faeteam4service.service;

import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.PersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.KnopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.PersonRepository;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class KnopfdruckServiceTest {

  private KnopfdruckService knopfdruckService;
  private KnopfRepository mockKnopfRepository;
  private PersonRepository mockPersonRepository;
  private PersonenService personenService;

  public KnopfdruckServiceTest(){
    mockKnopfRepository = Mockito.mock(KnopfRepository.class);
    mockPersonRepository = Mockito.mock(PersonRepository.class);

    personenService = new PersonenService(mockPersonRepository);
    knopfdruckService = new KnopfdruckService(mockKnopfRepository, personenService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenHandleKNopfdruckIsCalledAndHilfeKNopfIsUnknownIllegalArgumentExceptionShouldBeThrown() {

    String givenId= "someId";

    when(mockKnopfRepository.findById(givenId))
        .thenReturn(Optional.empty());

    knopfdruckService.handleKnopfdruck(givenId);

  }
}