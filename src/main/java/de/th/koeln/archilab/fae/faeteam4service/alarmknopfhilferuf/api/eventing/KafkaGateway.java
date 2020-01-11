package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
      HilferufEvent trackingEvent) {
    LOGGER.info("publishing event {} to topic {}", trackingEvent.getId(), topic);
    return kafkaTemplate.send(topic, trackingEvent.getKey(), toHilferufMessage(trackingEvent));
  }

  private String toHilferufMessage(HilferufEvent hilferufEvent) {
    try {
      final Map<String, Object> message = new HashMap<>();
      message.put("id", hilferufEvent.getId());
      message.put("key", hilferufEvent.getKey());
      message.put("time", hilferufEvent.getTimestamp());
      message.put("type", HilferufEvent.TYPE);
      message.put("version", HilferufEvent.VERSION);
      message.put("payload", objectMapper
          .readValue(hilferufEvent.getPayload(objectMapper), AlarmknopfHilferufDto.class));
      return objectMapper.writeValueAsString(message);
    } catch (final JsonProcessingException e) {
      LOGGER.error("Could not serialize event with id {}", hilferufEvent.getId(), e);
      return "";
    } catch (IOException e) {
      LOGGER.error("Could not read payload for event with id {}", hilferufEvent.getId(), e);
      return "";
    }
  }
}
