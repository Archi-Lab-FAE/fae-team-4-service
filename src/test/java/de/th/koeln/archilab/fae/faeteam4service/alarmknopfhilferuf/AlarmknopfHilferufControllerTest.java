package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.th.koeln.archilab.fae.faeteam4service.FaeTeam4ServiceApplication;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfdruckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FaeTeam4ServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AlarmknopfHilferufControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AlarmknopfRepository alarmknopfRepository;

  @MockBean
  private AlarmknopfdruckService mockAlarmknopfdruckService;

  @Test
  public void givenAlarmknopfId_whenHilferufMethodIsCalled_thenReturnHttp200() throws Exception {
    String alarmknopfId = "1337";
    String alarmknopfName = "myName";
    Position position = getPositionFromLatitudeAndLongitude(3.14, 4.13);

    Alarmknopf alarmknopf = new Alarmknopf(alarmknopfId, alarmknopfName, position);
    alarmknopfRepository.save(alarmknopf);

    mockMvc.perform(post("/alarmknoepfe/hilferuf/{alarmknopfId}", alarmknopfId))
        .andExpect(status().isOk());
  }

  private Position getPositionFromLatitudeAndLongitude(final double latitude,
      final double longitude) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengradDezimal(latitude);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengradDezimal(longitude);

    return new Position(breitengrad, laengengrad);
  }
}
