package de.th.koeln.archilab.fae.faeteam4service.tracker.api;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import de.th.koeln.archilab.fae.faeteam4service.tracker.TrackerService;
import de.th.koeln.archilab.fae.faeteam4service.tracker.persistence.Tracker;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TrackerController {

  private TrackerService trackerService;

  public TrackerController(TrackerService trackerService) {
    this.trackerService = trackerService;
  }

  @GetMapping(path = "tracker")
  public ResponseEntity<List<Tracker>> deleteTracker() {
    return new ResponseEntity<>(trackerService.getAllTrackers(), HttpStatus.OK);
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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<TrackerDto> handle(final Exception ex) {
    if (ex instanceof MappingException || ex instanceof HttpMessageNotReadableException) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  private TrackerDto createDtoFromTracker(final Tracker savedTracker) {
    Position savedTrackerPosition = savedTracker.getPosition();
    TrackerDto savedTrackerDto = new TrackerDto(null, null);

    if (savedTrackerPosition != null) {
      savedTrackerDto =
          new TrackerDto(
              savedTrackerPosition.getBreitengrad().getBreitengradDezimal(),
              savedTrackerPosition.getLaengengrad().getLaengengradDezimal());
    }

    return savedTrackerDto;
  }

  private Tracker createTrackerFrom(final String trackerId, final TrackerDto trackerDto) {
    return new Tracker(trackerId, trackerDto.getLaengengrad(), trackerDto.getBreitengrad());
  }
}
