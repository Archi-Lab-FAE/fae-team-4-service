package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaGateway {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaGateway.class);


  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final String topic;

  @Autowired
  public KafkaGateway(final KafkaTemplate<String, String> kafkaTemplate,
      final ObjectMapper objectMapper,
      @Value("${product.topic}") final String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.topic = topic;
  }

  public ListenableFuture<SendResult<String, String>> publishAlarmknopfHilferufAusgeloestEvent(
      final HilferufEvent hilferufEvent) {
    LOGGER.info("publishing event {} to topic {}", hilferufEvent.getId(), topic);
    return kafkaTemplate.send(topic, hilferufEvent.getKey(), toHilferufMessage(hilferufEvent));
  }

  private String toHilferufMessage(HilferufEvent hilferufEvent) {
    try {
      return objectMapper.writeValueAsString(hilferufEvent);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Could not serialize event with id {}", hilferufEvent.getId(), e);
      return "";
    }
  }
}
