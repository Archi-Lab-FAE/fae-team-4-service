package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfdruckService;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.MessagingServiceUnavailableException;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.Error;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmknopfHilferufController {

  private final AlarmknopfdruckService alarmknopfdruckService;
  private final ErrorRepository errorRepository;

  public AlarmknopfHilferufController(
      AlarmknopfdruckService alarmknopfdruckService, ErrorRepository errorRepository) {
    this.alarmknopfdruckService = alarmknopfdruckService;
    this.errorRepository = errorRepository;
  }

  @PostMapping(path = "/alarmknoepfe/hilferuf/{alarmknopfId}")
  public ResponseEntity hilferuf(@PathVariable String alarmknopfId) {
    alarmknopfdruckService.handleAlarmknopfdruck(new Alarmknopfdruck(alarmknopfId));
    return new ResponseEntity(HttpStatus.OK);
  }
}
