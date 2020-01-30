package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlarmknopfHilferufKafkaPublisher {

  private final ModelMapper modelMapper;
  private final AlarmknopfKafkaGateway eventPublisher;

  @Autowired
  public AlarmknopfHilferufKafkaPublisher(final ModelMapper modelMapper,
      final AlarmknopfKafkaGateway eventPublisher) {
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
