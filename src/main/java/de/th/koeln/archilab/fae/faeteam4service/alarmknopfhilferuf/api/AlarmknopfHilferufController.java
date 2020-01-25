package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.Alarmknopfdruck;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfdruckService;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.MessagingServiceUnavailableException;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

  @ExceptionHandler(MessagingServiceUnavailableException.class)
  public ResponseEntity<String> handle(final Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Could not reach adjacent service.");
  }
}
