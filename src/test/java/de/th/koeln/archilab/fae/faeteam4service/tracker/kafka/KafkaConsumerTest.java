package de.th.koeln.archilab.fae.faeteam4service.tracker.kafka;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.TrackerRepository;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DirtiesContext
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
    "spring.kafka.enabled=true"})
public class KafkaConsumerTest {

  private static String TOPIC = "tracker";

  @Autowired
  private TrackerRepository trackerRepository;

  private KafkaTemplate<String, String> template;

  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  @ClassRule
  public static EmbeddedKafkaRule embeddedKafka =
      new EmbeddedKafkaRule(1, true, TOPIC);

  @Before
  public void setUp() {
    Map<String, Object> senderProperties = KafkaTestUtils.senderProps(
        embeddedKafka.getEmbeddedKafka().getBrokersAsString());

    ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(
        senderProperties);

    template = new KafkaTemplate<>(producerFactory);
    template.setDefaultTopic(TOPIC);

    for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
        .getListenerContainers()) {
      ContainerTestUtils.waitForAssignment(messageListenerContainer,
          embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }
  }

  @Test
  public void testReceive() {
    final String greeting = "Hello Kafka";
    template.sendDefault(greeting);

    List<Tracker> tracker = trackerRepository.findAll();

    assertThat(tracker.size(), equalTo(1));
    assertThat(tracker.get(0).getId(), equalTo(greeting));
  }
}