package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import lombok.Getter;

public class Alarmknopfdruck {

  @Getter
  private String alarmknopfId;

  public Alarmknopfdruck(String alarmknopfId) {
    this.alarmknopfId = alarmknopfId;
  }
}
