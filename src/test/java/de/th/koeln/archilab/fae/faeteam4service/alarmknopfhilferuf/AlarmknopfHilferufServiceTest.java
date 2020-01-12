package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.api.eventing.KafkaPublisher;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class AlarmknopfHilferufServiceTest {

  private final KafkaPublisher mockKafkaPublisher;
  private final AlarmknopfHilferufService alarmknopfHilferufService;

  public AlarmknopfHilferufServiceTest() {
    this.mockKafkaPublisher = Mockito.mock(KafkaPublisher.class);
    this.alarmknopfHilferufService = new AlarmknopfHilferufService(mockKafkaPublisher);
  }

  @Test
  public void givenTrackerList_whenServiceIsCalled_thenKafkaPublisherIsCalledForEachTracker() {
    String[] trackerId = new String[]{"1", "2", "3", "4"};

    List<Tracker> trackerList = new ArrayList<>();
    for (String id : trackerId) {
      trackerList.add(new Tracker(id));
    }

    alarmknopfHilferufService.sendHilferufeForTracker(trackerList);

    ArgumentCaptor<AlarmknopfHilferuf> argument = ArgumentCaptor.forClass(AlarmknopfHilferuf.class);

    verify(mockKafkaPublisher, times(4))
        .publishAlarmknopfHilferufAusgeloestEvent(argument.capture());

    List<AlarmknopfHilferuf> capturedTrackersToSend = argument.getAllValues();

    for (int i = 0; i < trackerList.size(); i++) {
      assertEquals(trackerList.get(i).getId(), capturedTrackersToSend.get(i).getTrackerId());
    }
  }
}