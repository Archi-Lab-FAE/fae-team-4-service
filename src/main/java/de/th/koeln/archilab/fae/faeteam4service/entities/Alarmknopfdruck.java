package de.th.koeln.archilab.fae.faeteam4service.entities;

import lombok.Getter;

public class Alarmknopfdruck {

  @Getter
  private String alarmknopfId;

  public Alarmknopfdruck(String alarmknopfId) {
    this.alarmknopfId = alarmknopfId;
  }
}
