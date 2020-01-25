package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlarmknopfEventFactory {

  private ModelMapper modelMapper;
  private String updateType;
  private String deleteType;

  public AlarmknopfEventFactory(
      ModelMapper modelMapper,
      @Value("${spring.kafka.producer.alarmknopf.types.updateType}") String updateType,
      @Value("${spring.kafka.producer.alarmknopf.types.deleteType}") String deleteType) {
    this.modelMapper = modelMapper;
    this.updateType = updateType;
    this.deleteType = deleteType;
  }

  public AlarmknopfEvent createAlarmknopfUpdatedEvent(Alarmknopf alarmknopf) {
    AlarmknopfEvent alarmknopfEvent = new AlarmknopfEvent();

    alarmknopfEvent.setKey(alarmknopf.getId());
    alarmknopfEvent.setPayload(modelMapper.map(alarmknopf, AlarmknopfDto.class));
    alarmknopfEvent.setType(updateType);
    alarmknopfEvent.setVersion(0L);

    return alarmknopfEvent;
  }

  public AlarmknopfEvent createAlarmknopfDeletedEvent(Alarmknopf alarmknopf) {
    AlarmknopfEvent alarmknopfEvent = new AlarmknopfEvent();

    alarmknopfEvent.setKey(alarmknopf.getId());
    alarmknopfEvent.setType(deleteType);
    alarmknopfEvent.setVersion(0L);

    return alarmknopfEvent;
  }
}
