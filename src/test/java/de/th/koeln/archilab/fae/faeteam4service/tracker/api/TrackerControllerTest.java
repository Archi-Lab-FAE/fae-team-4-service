package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import de.th.koeln.archilab.fae.faeteam4service.FaeTeam4ServiceApplication;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FaeTeam4ServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TrackerControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private TrackerService trackerService;

  private static final String TEST_ID = "someId";

  @Test
  public void shouldDeleteTrackerAndReturnOkIfSuccessful() throws Exception {
    when(trackerService.deleteTracker(TEST_ID)).thenReturn(true);

    mockMvc.perform(delete("/tracker/{trackerId}", TEST_ID)).andExpect(status().isOk());
  }

  @Test
  public void shouldDeleteTrackerAndReturnNotFoundIfNotSuccessful() throws Exception {
    when(trackerService.deleteTracker(TEST_ID)).thenReturn(false);

    mockMvc.perform(delete("/tracker/{trackerId}", TEST_ID)).andExpect(status().isNotFound());
  }
}
