package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlarmknopfHilferufAlerterTest {

  private AlarmknopfHilferufAlerter alarmknopfHilferufAlerter;
  private RestTemplate mockRestTemplate;
  private AusnahmesituationFactory ausnahmesituationFactory;

  private AlarmknopfHilferuf testHilferuf;
  private Ausnahmesituation testAusnahmesituation;

  @Before
  public void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    ausnahmesituationFactory = mock(AusnahmesituationFactory.class);
    alarmknopfHilferufAlerter =
        new AlarmknopfHilferufAlerter(
            mockRestTemplate, ausnahmesituationFactory, mock(ErrorService.class));

    testHilferuf = new AlarmknopfHilferuf("trackerId");
    testAusnahmesituation = new Ausnahmesituation("someId", "someText");
    when(ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(testHilferuf))
        .thenReturn(testAusnahmesituation);
  }
}
