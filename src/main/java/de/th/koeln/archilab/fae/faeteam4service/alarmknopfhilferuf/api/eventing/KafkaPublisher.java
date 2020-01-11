package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

  private final ModelMapper modelMapper;
  private final KafkaGateway eventPublisher;

  @Autowired
  public KafkaPublisher(final ModelMapper modelMapper, final KafkaGateway eventPublisher) {
    this.modelMapper = modelMapper;
    this.eventPublisher = eventPublisher;
  }

  public void publishAlarmknopfHilferufAusgeloestEvent(
      final AlarmknopfHilferuf alarmknopfHilferuf) {
    HilferufEvent hilferufEvent = getHilferufEvent(alarmknopfHilferuf);
    eventPublisher.publishAlarmknopfHilferufAusgeloestEvent(hilferufEvent);
  }

  private HilferufEvent getHilferufEvent(final AlarmknopfHilferuf alarmknopfHilferuf) {
    AlarmknopfHilferufDto alarmknopfHilferufDto = convertToDto(alarmknopfHilferuf);
    return new HilferufEvent(alarmknopfHilferufDto);
  }

  private AlarmknopfHilferufDto convertToDto(final AlarmknopfHilferuf alarmknopfHilferuf) {
    return modelMapper.map(alarmknopfHilferuf, AlarmknopfHilferufDto.class);
  }
}
