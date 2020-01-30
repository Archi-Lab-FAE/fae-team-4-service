package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import com.netflix.discovery.EurekaClient;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.errorhandling.ErrorService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.*;

public class AlarmknopfHilferufAlerterTest {

  private AlarmknopfHilferufAlerter alarmknopfHilferufAlerter;
  private static final String TEST_URL = "someUrl";
  private RestTemplate mockRestTemplate;
  private AusnahmesituationFactory ausnahmesituationFactory;
  private DiscoveryClient mockDiscoveryClient;

  private AlarmknopfHilferuf testHilferuf;
  private Ausnahmesituation testAusnahmesituation;

  @Before
  public void setUp() {
    mockRestTemplate = mock(RestTemplate.class);
    ausnahmesituationFactory = mock(AusnahmesituationFactory.class);
    mockDiscoveryClient = mock(DiscoveryClient.class);
    alarmknopfHilferufAlerter =
        new AlarmknopfHilferufAlerter(
            mockRestTemplate,
            TEST_URL,
            ausnahmesituationFactory,
            mock(ErrorService.class),
            mockDiscoveryClient);

    testHilferuf = new AlarmknopfHilferuf("trackerId");
    testAusnahmesituation = new Ausnahmesituation("someId", "someText");
    when(ausnahmesituationFactory.createAusnahmesituationFromAlarmknopfHilferuf(testHilferuf))
        .thenReturn(testAusnahmesituation);
  }

}
