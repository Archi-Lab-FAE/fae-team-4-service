package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.rest;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfdruckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmknopfHilferufController {

  private final AlarmknopfdruckService alarmknopfdruckService;

  public AlarmknopfHilferufController(AlarmknopfdruckService alarmknopfdruckService) {
    this.alarmknopfdruckService = alarmknopfdruckService;
  }

  @PostMapping(path = "/alarmknoepfe/hilferuf/{alarmknopfId}")
  public ResponseEntity hilferuf(@PathVariable String alarmknopfId) {
    alarmknopfdruckService.handleAlarmknopfdruck(new Alarmknopfdruck(alarmknopfId));
    return new ResponseEntity(HttpStatus.OK);
  }

}
