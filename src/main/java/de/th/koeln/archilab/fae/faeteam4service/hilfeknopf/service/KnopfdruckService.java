package de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.service;

import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.HilfeKnopf;
import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.HilfeknopfId;
import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.HilfeknopfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnopfdruckService {

  private final HilfeknopfRepository hilfeknopfRepository;

  public KnopfdruckService(HilfeknopfRepository hilfeknopfRepository) {
    this.hilfeknopfRepository = hilfeknopfRepository;
  }

  /**
   * Gets Knopf by id from Repository.
   * @param hilfeKnopfIdString KnopfId
   */
  void handleKnopfdruck(String hilfeKnopfIdString) {

    HilfeknopfId hilfeknopfId = new HilfeknopfId(hilfeKnopfIdString);

    HilfeKnopf pressedHilfeknopf = hilfeknopfRepository.findById(hilfeknopfId)
        .orElseThrow(() -> new IllegalArgumentException("HilfeKnopf not found"));

  }


}
