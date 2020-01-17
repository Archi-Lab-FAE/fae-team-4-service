package de.th.koeln.archilab.fae.faeteam4service.tracker.api.eventing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DemenziellErkrankterConsumer {

  private static final Logger LOG = LoggerFactory.getLogger(DemenziellErkrankterConsumer.class);

  //@KafkaListener(topics = "demenziellErkrankter", autoStartup = "${spring.kafka.enabled}")
  public void consumeDemenziellErkrankte(@Payload DemenziellErkrankterDto data,
      @Headers MessageHeaders headers) {
    LOG.info("received data='{}'", data);

    headers.keySet().forEach(key -> LOG.info("{}: {}", key, headers.get(key)));
  }
}
