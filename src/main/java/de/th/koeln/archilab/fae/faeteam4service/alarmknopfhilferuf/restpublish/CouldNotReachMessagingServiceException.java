package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import org.springframework.web.client.RestClientException;

public class CouldNotReachMessagingServiceException extends RuntimeException {

  private final RestClientException restClientException;

  CouldNotReachMessagingServiceException(final RestClientException restClientException) {
    this.restClientException = restClientException;
  }

  @Override
  public String getMessage() {
    return "There was an Error connecting to an adjacent service. Exception: "
        + restClientException.getMessage();
  }
}
