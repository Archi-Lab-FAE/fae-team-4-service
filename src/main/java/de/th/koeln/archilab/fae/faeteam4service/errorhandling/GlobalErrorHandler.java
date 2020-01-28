package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

  private ErrorService errorService;

  public GlobalErrorHandler(ErrorService errorService) {
    this.errorService = errorService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handle(final Exception ex) {
    errorService.persistException(ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
