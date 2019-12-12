package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.FaeTeam4ServiceApplication;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfRegistrierungServiceImpl;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FaeTeam4ServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AlarmknopfRegistrierungControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AlarmknopfRegistrierungServiceImpl alarmknopfRegistrierungServiceImpl;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String ALARMKNOPF_ID = "1337";
  private static final String ALARMKNOPF_NAME = "myName";


  @Test
  public void shouldGetAllAlarmknoepfeAndReturnListIfSuccessful() throws Exception {
    double latitude = 3.14;
    double longitude = 4.13;
    double latitude1 = 33.14;
    double longitude1 = 44.13;

    Position position = getPositionFromLatitudeAndLongitude(latitude, longitude);
    Alarmknopf alarmknopf = new Alarmknopf(ALARMKNOPF_ID, ALARMKNOPF_NAME, position);

    Position position1 = getPositionFromLatitudeAndLongitude(latitude1, longitude1);
    Alarmknopf alarmknopf1 = new Alarmknopf(ALARMKNOPF_ID + "1", ALARMKNOPF_NAME + "1", position1);

    List<Alarmknopf> alarmknoepfe = new ArrayList<>();
    alarmknoepfe.add(alarmknopf);
    alarmknoepfe.add(alarmknopf1);

    when(alarmknopfRegistrierungServiceImpl.findAll()).thenReturn(alarmknoepfe);
    mockMvc.perform(get("/alarmknoepfe")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void givenAlarmknopf_whenGetAlarmknoepfe_thenReturnJsonWithAlarmknopfById()
      throws Exception {
    double latitude = 3.14;
    double longitude = 4.13;

    Position position = getPositionFromLatitudeAndLongitude(latitude, longitude);

    Alarmknopf alarmknopf = new Alarmknopf(ALARMKNOPF_ID, ALARMKNOPF_NAME, position);
    Optional<Alarmknopf> alarmknopfOptional = Optional.of(alarmknopf);
    given(alarmknopfRegistrierungServiceImpl.findById(ALARMKNOPF_ID))
        .willReturn(alarmknopfOptional);

    AlarmknopfDto alarmknopfDto = new AlarmknopfDto(ALARMKNOPF_ID, ALARMKNOPF_NAME,
        new PositionDto());

    mockMvc.perform(get("/alarmknoepfe/{alarmknopfId}", ALARMKNOPF_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(alarmknopfDto.getId())));
  }

  @Test
  public void givenEmptyRequestBody_whenPostAlarmknoepfe_thenHttp405ShouldBeReturned()
      throws Exception {
    mockMvc.perform(post("/alarmknoepfe").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isMethodNotAllowed());
  }

  @Test
  public void givenValidAlarmknopfId_whenDeleteAlarmknoepfe_thenHttp200ShouldBeReturned()
      throws Exception {
    when(alarmknopfRegistrierungServiceImpl.deleteById(ALARMKNOPF_ID)).thenReturn(true);
    mockMvc.perform(delete("/alarmknoepfe/{alarmknopfId}", ALARMKNOPF_ID))
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidAlarmknopfId_whenDeleteAlarmknoepfe_thenHttp404ShouldBeReturned()
      throws Exception {
    when(alarmknopfRegistrierungServiceImpl.deleteById(ALARMKNOPF_ID)).thenReturn(false);
    mockMvc.perform(delete("/alarmknoepfe/{alarmknopfId}", ALARMKNOPF_ID))
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenAlarmknopf_whenPostAlarmknopf_thenHttp201AndJsonWithAlarmknopfShouldBeReturned()
      throws Exception {
    PositionDto positionDto = new PositionDto(3.14, 4.13);
    AlarmknopfDto alarmknopfDto = new AlarmknopfDto(ALARMKNOPF_ID, ALARMKNOPF_NAME, positionDto);

    when(alarmknopfRegistrierungServiceImpl.save(any(Alarmknopf.class))).thenReturn(true);

    mockMvc.perform(post("/alarmknoepfe/").content(objectMapper.writeValueAsString(alarmknopfDto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(alarmknopfDto.getId())))
        .andExpect(jsonPath("$.position.breitengrad", is(positionDto.getBreitengrad())))
        .andExpect(jsonPath("$.position.laengengrad", is(positionDto.getLaengengrad())));
  }

  @Test
  public void givenNotMapableObject_whenPostInvalidJson_thenHttp400ShouldBeReturned()
      throws Exception {
    File file = ResourceUtils.getFile("classpath:AlarmknopfInvalidRequestBody.json");
    String requestBody = new String(Files.readAllBytes(file.toPath()));

    List<ErrorMessage> errorMessages = new ArrayList<>();
    errorMessages.add(new ErrorMessage("mapping failed"));

    when(alarmknopfRegistrierungServiceImpl.save(any())).thenThrow(new MappingException(errorMessages));

    mockMvc.perform(post("/alarmknoepfe/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
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
