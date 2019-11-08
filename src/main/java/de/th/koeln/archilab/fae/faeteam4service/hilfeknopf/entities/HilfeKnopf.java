package de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities;

import de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities.standort.Standort;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HilfeKnopf {

  @Id
  @Embedded
  HilfeknopfId hilfeknopfID;


  Standort standort;

}
