package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.entities.external.DementiellErkranktePerson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

  /**
  @KafkaListener(topics = "Dummy_Topic",
      containerFactory = "dementiellErkranktePersonKafkaListenerFactory")
  public void consumeDementiellErkranktePersonTopic(final String id,
      final DementiellErkranktePerson dementiellErkranktePerson) {

  }
  **/
}
