package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing.KafkaGateway;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AlarmknopfKafkaPublisherTest {

  private AlarmknopfKafkaPublisher alarmknopfKafkaPublisher;
  private KafkaGateway mockKafkaGateway;

  private AlarmknopfEventFactory mockAlarmknopfEventFactory;

  @Before
  public void setUp() {
    mockAlarmknopfEventFactory = mock(AlarmknopfEventFactory.class);
    mockKafkaGateway = mock(KafkaGateway.class);

    alarmknopfKafkaPublisher =
        new AlarmknopfKafkaPublisher(mockKafkaGateway, mockAlarmknopfEventFactory);
  }

  @Test
  public void whenPublishDeletedAlarmknopfIsCalledShouldPublishDeletedEvent() {
    Alarmknopf alarmknopf = new Alarmknopf();
    AlarmknopfEvent alarmknopfDeletedEvent = new AlarmknopfEvent();
    when(mockAlarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf))
        .thenReturn(alarmknopfDeletedEvent);

    alarmknopfKafkaPublisher.publishDeletedAlarmknopf(alarmknopf);

    verify(mockKafkaGateway).publishAlarmknopfEvent(alarmknopfDeletedEvent);
  }

  @Test
  public void whenPublishUpdatedAlarmknopfIsCalledShouldPublishUpdatedEvent() {
    Alarmknopf alarmknopf = new Alarmknopf();
    AlarmknopfEvent alarmknopfUpdatedEvent = new AlarmknopfEvent();
    when(mockAlarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf))
        .thenReturn(alarmknopfUpdatedEvent);

    alarmknopfKafkaPublisher.publishUpdatedAlarmknopf(alarmknopf);

    verify(mockKafkaGateway).publishAlarmknopfEvent(alarmknopfUpdatedEvent);
  }
}
