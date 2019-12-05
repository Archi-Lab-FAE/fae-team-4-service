package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class TrackerDto {
  private Double laengengrad;
  private Double breitengrad;
}
