package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam4service.FaeTeam4ServiceApplication;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.MappingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FaeTeam4ServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TrackerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();
  @MockBean
  private TrackerService trackerService;

  private static final String TEST_ID = "someId";
  private String urlTemplate = "/tracker/{trackerId}";

  @Test
  public void shouldDeleteTrackerAndReturnOkIfSuccessful() throws Exception {
    when(trackerService.deleteTracker(TEST_ID)).thenReturn(true);

    mockMvc.perform(delete(urlTemplate, TEST_ID)).andExpect(status().isOk());
  }

  @Test
  public void shouldDeleteTrackerAndReturnNotFoundIfNotSuccessful() throws Exception {
    when(trackerService.deleteTracker(TEST_ID)).thenReturn(false);

    mockMvc.perform(delete(urlTemplate, TEST_ID)).andExpect(status().isNotFound());
  }

  @Test
  public void givenValidTracker_whenPutTracker_thenPersistTrackerAndReturnHttp200WithEntity()
      throws Exception {
    String id = "myIdForTest";
    double laengengrad = 41.44;
    double breitengrad = 55.011;

    Position position = new Position(breitengrad, laengengrad);
    Tracker tracker = new Tracker(id, position);

    TrackerDto trackerDto = new TrackerDto(breitengrad, laengengrad);
    String json = objectMapper.writeValueAsString(trackerDto);

    when(trackerService.updateOrCreateTracker(any())).thenReturn(tracker);
    mockMvc.perform(put(urlTemplate, TEST_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(content().json(json))
        .andExpect(status().isOk());
  }

  @Test
  public void givenNotMapableObject_whenPutInvalidJson_thenHttp400ShouldBeReturned()
      throws Exception {
    File file = ResourceUtils.getFile("classpath:TrackerInvalidRequestBody.json");
    String requestBody = new String(Files.readAllBytes(file.toPath()));

    List<ErrorMessage> errorMessages = new ArrayList<>();
    errorMessages.add(new ErrorMessage("mapping failed"));

    when(trackerService.updateOrCreateTracker(any()))
        .thenThrow(new MappingException(errorMessages));

    mockMvc.perform(put(urlTemplate, TEST_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void givenNotReadable_whenPutNotReadableJson_thenHttp400ShouldBeReturned()
      throws Exception {
    File file = ResourceUtils.getFile("classpath:GenericNotReadableRequestBody.json");
    String requestBody = new String(Files.readAllBytes(file.toPath()));

    HttpInputMessage httpInputMessage = getMockHttpInputMessage();

    when(trackerService.updateOrCreateTracker(any()))
        .thenThrow(new HttpMessageNotReadableException("not readable", httpInputMessage));

    mockMvc.perform(put(urlTemplate, TEST_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private HttpInputMessage getMockHttpInputMessage() {
    return new HttpInputMessage() {
      @Override
      public InputStream getBody() {
        return null;
      }

      @Override
      public HttpHeaders getHeaders() {
        return null;
      }
    };
  }
}
