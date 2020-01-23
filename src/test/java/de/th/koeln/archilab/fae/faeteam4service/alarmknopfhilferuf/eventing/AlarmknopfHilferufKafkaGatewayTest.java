package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.eventing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing.AlarmknopfEvent;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferufDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmknopfHilferufKafkaGatewayTest {

  @Mock
  private KafkaTemplate<String, String> mockKafkaTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String ALARMKNOPF_HILFERUF_TOPIC = "TestTopic";
  private static final String ALARMKNOPF_TOPIC = "TestTopic2";
  private static final String TRACKER_ID = "myTestTrackerId";

  @Test
  public void publishAlarmknopfHilferuf() {
    AlarmknopfHilferufDto alarmknopfHilferufDto = new AlarmknopfHilferufDto(TRACKER_ID);

    HilferufEvent expectedHilferufEvent = new HilferufEvent(alarmknopfHilferufDto);
    AlarmknopfHilferufKafkaGateway alarmknopfHilferufKafkaGateway =
        new AlarmknopfHilferufKafkaGateway(
            mockKafkaTemplate, objectMapper, ALARMKNOPF_HILFERUF_TOPIC, ALARMKNOPF_TOPIC);

    alarmknopfHilferufKafkaGateway.publishAlarmknopfHilferufAusgeloestEvent(expectedHilferufEvent);

    ArgumentCaptor<String> topicArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> keyArgument = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> messageArgument = ArgumentCaptor.forClass(String.class);

    verify(mockKafkaTemplate, times(1))
        .send(topicArgument.capture(), keyArgument.capture(), messageArgument.capture());

    String expectedTrackerIdLineInJson = "\"trackerId\" : \"" + TRACKER_ID + "\",";

    assertEquals(ALARMKNOPF_HILFERUF_TOPIC, topicArgument.getValue());
    assertNotNull(keyArgument.getValue());
    assertTrue(messageArgument.getValue().contains(expectedTrackerIdLineInJson));
  }

  @Test
  public void publishAlarmknopf() {
    AlarmknopfEvent alarmknopfEvent = new AlarmknopfEvent();
    AlarmknopfHilferufKafkaGateway alarmknopfHilferufKafkaGateway =
        new AlarmknopfHilferufKafkaGateway(
            mockKafkaTemplate, objectMapper, ALARMKNOPF_HILFERUF_TOPIC, ALARMKNOPF_TOPIC);

    alarmknopfHilferufKafkaGateway.publishAlarmknopfEvent(alarmknopfEvent);

    verify(mockKafkaTemplate, times(1)).send(eq(ALARMKNOPF_TOPIC), any(), any());
  }
}
