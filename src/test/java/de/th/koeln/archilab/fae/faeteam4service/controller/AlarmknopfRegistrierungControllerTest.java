package de.th.koeln.archilab.fae.faeteam4service.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.FaeTeam4ServiceApplication;
import de.th.koeln.archilab.fae.faeteam4service.dto.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.dto.position.PositionDto;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FaeTeam4ServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AlarmknopfRegistrierungControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AlarmknopfRepository alarmknopfRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String ALARMKNOPF_ID = "1337";
  private static final String ALARMKNOPF_NAME = "myName";

  @Test
  public void givenAlarmknopf_whenGetAlarmknoepfe_thenReturnJsonWithAlarmknopfById()
      throws Exception {

    String alarmknopfId = "1337";
    Position position = getPositionFromLatitudeAndLongitude(3.14, 4.13);

    Alarmknopf alarmknopf = new Alarmknopf(ALARMKNOPF_ID, ALARMKNOPF_NAME, position);
    alarmknopfRepository.save(alarmknopf);

    AlarmknopfDto alarmknopfDto = new AlarmknopfDto(ALARMKNOPF_ID, ALARMKNOPF_NAME,
        new PositionDto());

    mockMvc.perform(get("/alarmknoepfe/{alarmknopfId}", alarmknopfId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(alarmknopfDto.getId())));
  }

  @Test
  public void givenAlarmknopf_whenDeleteAlarmknopfById_thenRemoveAlarmknopf() throws Exception {
    String alarmknopfId = "1337";
    String alarmknopfName = "myName";
    Position position = getPositionFromLatitudeAndLongitude(3.14, 4.13);

    Alarmknopf alarmknopf = new Alarmknopf(alarmknopfId, alarmknopfName, position);
    alarmknopfRepository.save(alarmknopf);

    mockMvc.perform(delete("/alarmknoepfe/{alarmknopfId}", alarmknopfId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    assertThat(alarmknopfRepository.existsById(alarmknopfId), equalTo(false));
  }

  @Test
  public void givenNone_whenRegisterAlarmknopf_thenReturnJsonWithAlarmknopf() throws Exception {
    PositionDto positionDto = new PositionDto(3.14, 4.13);
    AlarmknopfDto alarmknopfDto = new AlarmknopfDto(ALARMKNOPF_ID, ALARMKNOPF_NAME, positionDto);

    mockMvc.perform(post("/alarmknoepfe/").content(objectMapper.writeValueAsString(alarmknopfDto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(alarmknopfDto.getId())))
        .andExpect(jsonPath("$.position.breitengrad.breitengradDezimal",
            is(positionDto.getBreitengrad().getBreitengradDezimal())))
        .andExpect(jsonPath("$.position.laengengrad.laengengradDezimal",
            is(positionDto.getLaengengrad().getLaengengradDezimal())));
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
