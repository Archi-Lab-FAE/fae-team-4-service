package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import org.springframework.stereotype.Service;

@Service
public class ErrorService {

  private ErrorRepository errorRepository;

  public ErrorService(ErrorRepository errorRepository) {
    this.errorRepository = errorRepository;
  }

  public void persistException(Exception exception) {
    errorRepository.save(new Error(exception));
  }

  public void persistString(String string) {
    Exception exception = new Exception(string);
    errorRepository.save(new Error(exception));
  }
}
