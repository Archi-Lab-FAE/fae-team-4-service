package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfKafkaGateway;
import org.junit.Before;
import org.junit.Test;

public class AlarmknopfAlarmknopfHilferufKafkaPublisherTest {

  private AlarmknopfKafkaPublisher alarmknopfKafkaPublisher;
  private AlarmknopfKafkaGateway mockAlarmknopfKafkaGateway;

  private AlarmknopfEventFactory mockAlarmknopfEventFactory;

  @Before
  public void setUp() {
    mockAlarmknopfEventFactory = mock(AlarmknopfEventFactory.class);
    mockAlarmknopfKafkaGateway = mock(AlarmknopfKafkaGateway.class);

    alarmknopfKafkaPublisher =
        new AlarmknopfKafkaPublisher(mockAlarmknopfKafkaGateway,
            mockAlarmknopfEventFactory);
  }

  @Test
  public void whenPublishDeletedAlarmknopfIsCalledShouldPublishDeletedEvent() {
    Alarmknopf alarmknopf = new Alarmknopf();
    AlarmknopfEvent alarmknopfDeletedEvent = new AlarmknopfEvent();
    when(mockAlarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf))
        .thenReturn(alarmknopfDeletedEvent);

    alarmknopfKafkaPublisher.publishDeletedAlarmknopf(alarmknopf);

    verify(mockAlarmknopfKafkaGateway).publishAlarmknopfEvent(alarmknopfDeletedEvent);
  }

  @Test
  public void whenPublishUpdatedAlarmknopfIsCalledShouldPublishUpdatedEvent() {
    Alarmknopf alarmknopf = new Alarmknopf();
    AlarmknopfEvent alarmknopfUpdatedEvent = new AlarmknopfEvent();
    when(mockAlarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf))
        .thenReturn(alarmknopfUpdatedEvent);

    alarmknopfKafkaPublisher.publishUpdatedAlarmknopf(alarmknopf);

    verify(mockAlarmknopfKafkaGateway).publishAlarmknopfEvent(alarmknopfUpdatedEvent);
  }
}
