package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class TrackerDto {
  private Double breitengrad;
  private Double laengengrad;
}
