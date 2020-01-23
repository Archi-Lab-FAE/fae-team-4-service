package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfHilferufKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.AlarmknopfHilferufAlerter;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class AlarmknopfHilferufServiceTest {

  private final AlarmknopfHilferufKafkaPublisher mockAlarmknopfHilferufKafkaPublisher;
  private final AlarmknopfHilferufAlerter mockAlarmknopfHilferufAlerter;
  private final AlarmknopfHilferufService alarmknopfHilferufService;

  public AlarmknopfHilferufServiceTest() {
    this.mockAlarmknopfHilferufKafkaPublisher = Mockito
        .mock(AlarmknopfHilferufKafkaPublisher.class);
    this.mockAlarmknopfHilferufAlerter = Mockito.mock(AlarmknopfHilferufAlerter.class);
    this.alarmknopfHilferufService =
        new AlarmknopfHilferufService(mockAlarmknopfHilferufKafkaPublisher,
            mockAlarmknopfHilferufAlerter);
  }

  @Test
  public void givenTrackerList_whenServiceIsCalled_thenKafkaPublisherIsCalledForEachTracker() {
    String[] trackerId = new String[] {"1", "2", "3", "4"};

    List<Tracker> trackerList = new ArrayList<>();
    for (String id : trackerId) {
      trackerList.add(new Tracker(id));
    }

    alarmknopfHilferufService.sendHilferufeForTracker(trackerList);

    ArgumentCaptor<AlarmknopfHilferuf> argument = ArgumentCaptor.forClass(AlarmknopfHilferuf.class);

    verify(mockAlarmknopfHilferufKafkaPublisher, times(4))
        .publishAlarmknopfHilferufAusgeloestEvent(argument.capture());

    List<AlarmknopfHilferuf> capturedTrackersToSend = argument.getAllValues();

    for (int i = 0; i < trackerList.size(); i++) {
      assertEquals(trackerList.get(i).getId(), capturedTrackersToSend.get(i).getTrackerId());
    }
  }

  @Test
  public void givenTrackerList_whenServiceIsCalled_thenHilferufAlerterIsCalledForEachTracker() {
    String[] trackerId = new String[] {"1", "2", "3", "4"};

    List<Tracker> trackerList = new ArrayList<>();
    for (String id : trackerId) {
      trackerList.add(new Tracker(id));
    }

    alarmknopfHilferufService.sendHilferufeForTracker(trackerList);

    ArgumentCaptor<AlarmknopfHilferuf> argument = ArgumentCaptor.forClass(AlarmknopfHilferuf.class);

    verify(mockAlarmknopfHilferufAlerter, times(4))
            .alertMessagingSystemAboutAlarmknopfHilferuf(argument.capture());

    List<AlarmknopfHilferuf> capturedTrackersToSend = argument.getAllValues();

    for (int i = 0; i < trackerList.size(); i++) {
      assertEquals(trackerList.get(i).getId(), capturedTrackersToSend.get(i).getTrackerId());
    }
  }
}
