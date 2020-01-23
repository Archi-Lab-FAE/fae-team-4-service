package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing.AlarmknopfEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class AlarmknopfKafkaGateway {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(AlarmknopfKafkaGateway.class);

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final String alarmknopfHilferufTopic;
  private final String alarmknopfTopic;

  @Autowired
  public AlarmknopfKafkaGateway(
      final KafkaTemplate<String, String> kafkaTemplate,
      final ObjectMapper objectMapper,
      @Value("${product.topic}") final String alarmknopfHilferufTopic,
      @Value("${alarmknopf.topic}") final String alarmknopfTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.alarmknopfHilferufTopic = alarmknopfHilferufTopic;
    this.alarmknopfTopic = alarmknopfTopic;
  }

  public ListenableFuture<SendResult<String, String>> publishAlarmknopfHilferufAusgeloestEvent(
      final HilferufEvent hilferufEvent) {
    LOGGER.info("publishing event {} to topic {}", hilferufEvent.getId(), alarmknopfHilferufTopic);
    return kafkaTemplate.send(
        alarmknopfHilferufTopic, hilferufEvent.getKey(), toMessageString(hilferufEvent));
  }

  public ListenableFuture<SendResult<String, String>> publishAlarmknopfEvent(
      final AlarmknopfEvent alarmknopfEvent) {
    LOGGER.info("publishing event {} to topic {}", alarmknopfEvent.getId(), alarmknopfTopic);
    return kafkaTemplate.send(
        alarmknopfTopic, alarmknopfEvent.getKey(), toMessageString(alarmknopfEvent));
  }

  private String toMessageString(Object event) {
    try {
      return objectMapper.writeValueAsString(event);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Could not serialize event {}", event.toString(), e);
      return "";
    }
  }
}
