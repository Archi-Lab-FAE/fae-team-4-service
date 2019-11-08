package de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class HilfeknopfId {

  private String id;

  public HilfeknopfId(String id){
    this.id = id;
  }

}
