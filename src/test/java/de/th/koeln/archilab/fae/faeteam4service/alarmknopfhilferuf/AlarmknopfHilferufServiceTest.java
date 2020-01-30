package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing.AlarmknopfHilferufKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish.MessagingServiceClient;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class AlarmknopfHilferufServiceTest {

  private final AlarmknopfHilferufKafkaPublisher mockAlarmknopfHilferufKafkaPublisher;
  private final MessagingServiceClient mockMessagingServiceClient;
  private final AlarmknopfHilferufService alarmknopfHilferufService;

  private final Alarmknopf testAlarmknopf;

  public AlarmknopfHilferufServiceTest() {
    this.mockAlarmknopfHilferufKafkaPublisher =
        Mockito.mock(AlarmknopfHilferufKafkaPublisher.class);
    this.mockMessagingServiceClient = Mockito.mock(MessagingServiceClient.class);
    this.alarmknopfHilferufService =
        new AlarmknopfHilferufService(
            mockAlarmknopfHilferufKafkaPublisher, mockMessagingServiceClient);
    testAlarmknopf = mock(Alarmknopf.class);
  }

  @Test
  public void givenTrackerList_whenServiceIsCalled_thenKafkaPublisherIsCalledForEachTracker() {
    String[] trackerId = new String[] {"1", "2", "3", "4"};

    List<Tracker> trackerList = new ArrayList<>();
    for (String id : trackerId) {
      trackerList.add(new Tracker(id));
    }

    alarmknopfHilferufService.sendHilferufeForTracker(trackerList, testAlarmknopf);

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

    alarmknopfHilferufService.sendHilferufeForTracker(trackerList, testAlarmknopf);

    ArgumentCaptor<AlarmknopfHilferuf> argument = ArgumentCaptor.forClass(AlarmknopfHilferuf.class);

    verify(mockMessagingServiceClient, times(4))
        .alertMessagingSystemAboutAlarmknopfHilferuf(argument.capture(), eq(testAlarmknopf));

    List<AlarmknopfHilferuf> capturedTrackersToSend = argument.getAllValues();

    for (int i = 0; i < trackerList.size(); i++) {
      assertEquals(trackerList.get(i).getId(), capturedTrackersToSend.get(i).getTrackerId());
    }
  }
}
