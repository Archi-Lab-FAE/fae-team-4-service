package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ErrorController {

  private ErrorRepository errorRepository;

  public ErrorController(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  @GetMapping(path = "/errors")
  public List<Error> getErrors() {
    return errorRepository.findAll();
  }
}
