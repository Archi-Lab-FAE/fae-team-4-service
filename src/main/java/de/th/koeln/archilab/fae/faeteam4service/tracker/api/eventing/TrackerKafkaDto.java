package de.th.koeln.archilab.fae.faeteam4service.tracker.api.eventing;

import lombok.Getter;

public class TrackerKafkaDto {

  @Getter
  private String id;

  @Getter
  private String besitzerId;

  @Getter
  private String letzterWartungstermin;
}
