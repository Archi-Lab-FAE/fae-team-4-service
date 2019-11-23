package de.th.koeln.archilab.fae.faeteam4service.config;

import de.th.koeln.archilab.fae.faeteam4service.entities.external.DementiellErkranktePerson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class Kafka {

  private static final String KAFKA_SERVER_ADRESS = "10.0.0.1:9092"; //TODO set adress

  @Bean
  public ProducerFactory<String, List> producerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_ADRESS);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean
  public KafkaTemplate<String, List> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }



  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_ADRESS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, DementiellErkranktePerson>
      dementiellErkranktePersonConsumerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
        new JsonDeserializer<>(DementiellErkranktePerson.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, DementiellErkranktePerson>
      dementiellErkranktePersonKafkaListenerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, DementiellErkranktePerson> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(dementiellErkranktePersonConsumerFactory());

    return factory;
  }
}
