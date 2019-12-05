package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TrackerController {

  private TrackerService trackerService;

  public TrackerController(TrackerService trackerService) {
    this.trackerService = trackerService;
  }

  @DeleteMapping(path = "tracker/{trackerId}")
  public ResponseEntity deleteTracker(@PathVariable String trackerId) {
    boolean wasDeletionSuccessful = trackerService.deleteTracker(trackerId);

    if (wasDeletionSuccessful) {
      return new ResponseEntity(HttpStatus.OK);
    }

    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @PutMapping(path = "tracker/{trackerId}")
  public ResponseEntity<TrackerDto> updateTracker(
      @PathVariable String trackerId, @RequestBody TrackerDto trackerDto) {
    Tracker newVersionOfTracker = createTrackerFrom(trackerId, trackerDto);
    Tracker savedTracker = trackerService.updateOrCreateTracker(newVersionOfTracker);
    TrackerDto dtoFromSavedTracker = createDtoFromTracker(savedTracker);
    return new ResponseEntity<>(dtoFromSavedTracker, HttpStatus.OK);
  }

  private TrackerDto createDtoFromTracker(Tracker savedTracker) {
    Position savedTrackerPosition = savedTracker.getPosition();
    TrackerDto savedTrackerDto = new TrackerDto(null, null);

    if (savedTrackerPosition != null) {
      savedTrackerDto =
          new TrackerDto(
              savedTrackerPosition.getLaengengrad().getLaengengradDezimal(),
              savedTrackerPosition.getBreitengrad().getBreitengradDezimal());
    }

    return savedTrackerDto;
  }

  private Tracker createTrackerFrom(String trackerId, TrackerDto trackerDto) {
    return new Tracker(
        trackerId, trackerDto.getLaengengrad(), trackerDto.getBreitengrad());
  }
}
