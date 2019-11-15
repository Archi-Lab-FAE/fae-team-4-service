package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.PersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.Knopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.KnopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.Person;
import org.springframework.stereotype.Service;

@Service
public class KnopfdruckService {

  private final KnopfRepository knopfRepository;
  private final PersonenService personenService;

  public KnopfdruckService(final KnopfRepository knopfRepository,
      final PersonenService personenService) {
    this.knopfRepository = knopfRepository;
    this.personenService = personenService;
  }

  /**
   * Gets Knopf by id from Repository.
   * @param hilfeKnopfIdString KnopfId
   */
  void handleKnopfdruck(final String hilfeKnopfIdString) {
    Knopf pressedKnopf = knopfRepository.findById(hilfeKnopfIdString)
        .orElseThrow(() -> new IllegalArgumentException("HilfeKnopf not found"));

  }


  private Iterable<Person> getPersonenInKnopfProximity(final Knopf knopf) {
    double radiusInMeters = 5;
    return personenService.getPersonenInProximityOf(knopf, radiusInMeters);
  }
}
