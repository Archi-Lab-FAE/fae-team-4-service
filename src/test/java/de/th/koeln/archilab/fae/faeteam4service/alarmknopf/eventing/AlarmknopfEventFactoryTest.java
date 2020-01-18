package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.eventing;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlarmknopfEventFactoryTest {

  private AlarmknopfEventFactory alarmknopfEventFactory;
  private ModelMapper mockModelMapper;

  private Alarmknopf alarmknopf;
  private static final String TEST_UPDATE_TYPE = "updateType";
  private static final String TEST_DELETE_TYPE = "deleteType";
  private static final String TEST_ID = "someId";

  @Before
  public void setUp() {
    mockModelMapper = mock(ModelMapper.class);
    alarmknopfEventFactory =
        new AlarmknopfEventFactory(mockModelMapper, TEST_UPDATE_TYPE, TEST_DELETE_TYPE);

    alarmknopf = new Alarmknopf();
    alarmknopf.setId(TEST_ID);
  }

  @Test
  public void whenUpdatedAlarmknopfEventIsCalledShouldCreateUpdatedEvent() {
    AlarmknopfDto alarmknopfDto = new AlarmknopfDto();
    when(mockModelMapper.map(alarmknopf, AlarmknopfDto.class)).thenReturn(alarmknopfDto);

    AlarmknopfEvent createdEvent = alarmknopfEventFactory.createAlarmknopfUpdatedEvent(alarmknopf);

    assertThat(createdEvent.getKey(), equalTo(TEST_ID));
    assertThat(createdEvent.getPayload(), equalTo(alarmknopfDto));
    assertThat(createdEvent.getVersion(), equalTo(0L));
    assertThat(createdEvent.getType(), equalTo(TEST_UPDATE_TYPE));
  }

  @Test
  public void whenDeletedAlarmknopfEventIsCalledShouldCreateDeletedEvent() {
    AlarmknopfEvent createdEvent = alarmknopfEventFactory.createAlarmknopfDeletedEvent(alarmknopf);

    assertThat(createdEvent.getKey(), equalTo(TEST_ID));
    assertThat(createdEvent.getPayload(), is(nullValue()));
    assertThat(createdEvent.getVersion(), equalTo(0L));
    assertThat(createdEvent.getType(), equalTo(TEST_DELETE_TYPE));
  }
}
