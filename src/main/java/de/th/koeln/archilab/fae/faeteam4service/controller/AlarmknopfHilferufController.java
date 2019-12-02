package de.th.koeln.archilab.fae.faeteam4service.controller;

import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.service.AlarmknopfdruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmknopfHilferufController {

  private AlarmknopfdruckService alarmknopfdruckService;

  public AlarmknopfHilferufController(AlarmknopfdruckService alarmknopfdruckService) {
    this.alarmknopfdruckService = alarmknopfdruckService;
  }

  @GetMapping(path = "/alarmknoepfe/hilferuf/{alarmknopfId}")
  public ResponseEntity hilferuf(@PathVariable String alarmknopfId) {
    alarmknopfdruckService.handleAlarmknopfdruck(new Alarmknopfdruck(alarmknopfId));
    return new ResponseEntity(HttpStatus.OK);
  }

}
