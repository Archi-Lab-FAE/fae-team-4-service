package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;

public class AlarmknopfHilferufKafkaPublisherTest {

  private final AlarmknopfHilferufKafkaPublisher alarmknopfHilferufKafkaPublisher;

  private final AlarmknopfHilferufKafkaGateway mockEventPublisher = mock(
      AlarmknopfHilferufKafkaGateway.class);
  private final ModelMapper mockModelMapper = mock(ModelMapper.class);

  public AlarmknopfHilferufKafkaPublisherTest() {
    this.alarmknopfHilferufKafkaPublisher = new AlarmknopfHilferufKafkaPublisher(mockModelMapper,
        mockEventPublisher);
  }

  @Test
  public void givenAlarmknopfHilferuf_whenPublisherMethodIsCalled_thenHilferufEventIsCreated() {
    String trackerId = "trackerId";
    AlarmknopfHilferuf alarmknopfHilferuf = new AlarmknopfHilferuf(trackerId);
    AlarmknopfHilferufDto alarmknopfHilferufDto = new AlarmknopfHilferufDto(trackerId);

    when(mockModelMapper.map(alarmknopfHilferuf, AlarmknopfHilferufDto.class))
        .thenReturn(alarmknopfHilferufDto);

    alarmknopfHilferufKafkaPublisher.publishAlarmknopfHilferufAusgeloestEvent(alarmknopfHilferuf);

    HilferufEvent hilferufEvent = new HilferufEvent(alarmknopfHilferufDto);
    ArgumentCaptor<HilferufEvent> argument = ArgumentCaptor.forClass(HilferufEvent.class);

    verify(mockEventPublisher, times(1))
        .publishAlarmknopfHilferufAusgeloestEvent(argument.capture());
    assertEquals(hilferufEvent.getPayload(), argument.getValue().getPayload());
  }
}