package de.th.koeln.archilab.fae.faeteam4service.tracker.eventing.consumer;

import lombok.Getter;

@Getter
public enum EventType {
  CREATED("created"),
  UPDATED("updated"),
  DELETED("deleted");

  private String eventString;

  EventType(String eventString) {
    this.eventString = eventString;
  }
}
