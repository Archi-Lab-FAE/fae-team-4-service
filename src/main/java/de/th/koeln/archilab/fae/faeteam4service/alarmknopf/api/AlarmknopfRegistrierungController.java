package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfRegistrierungServiceImpl;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmknopfRegistrierungController {

  private final AlarmknopfRegistrierungServiceImpl alarmknopfRegistrierungServiceImpl;
  private final ModelMapper modelMapper;


  public AlarmknopfRegistrierungController(
      AlarmknopfRegistrierungServiceImpl alarmknopfRegistrierungServiceImpl,
      ModelMapper modelMapper) {
    this.alarmknopfRegistrierungServiceImpl = alarmknopfRegistrierungServiceImpl;
    this.modelMapper = modelMapper;
  }

  @GetMapping(path = "/alarmknoepfe", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<AlarmknopfDto> getAlarmknoepfe() {
    List<Alarmknopf> alarmknoepfe = alarmknopfRegistrierungServiceImpl.findAll();
    return getAlarmknoepfeDto(alarmknoepfe);
  }

  @GetMapping(path = "/alarmknoepfe/{alarmknopfId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AlarmknopfDto> getAlarmknopf(@PathVariable String alarmknopfId) {
    Optional<Alarmknopf> alarmknopf = alarmknopfRegistrierungServiceImpl.findById(alarmknopfId);

    return alarmknopf
        .map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping(path = "/alarmknoepfe/",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity registerAlarmknopf(@RequestBody AlarmknopfDto alarmknopfDto) {
    Alarmknopf alarmknopf = convertToEntity(alarmknopfDto);
    boolean wasPersistSuccessful = alarmknopfRegistrierungServiceImpl.save(alarmknopf);

    if (wasPersistSuccessful) {
      return new ResponseEntity<>(alarmknopfDto, HttpStatus.CREATED);
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(path = "/alarmknoepfe/{alarmknopfId}")
  public ResponseEntity deleteOrder(@PathVariable String alarmknopfId) {
    boolean wasDeletionSuccessful = alarmknopfRegistrierungServiceImpl.deleteById(alarmknopfId);

    if (wasDeletionSuccessful) {
      return new ResponseEntity(HttpStatus.OK);
    }
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<AlarmknopfDto> handle(Exception ex, HttpServletRequest request,
      HttpServletResponse response) {
    if (ex instanceof NullPointerException || ex instanceof MappingException) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  private List<AlarmknopfDto> getAlarmknoepfeDto(Iterable<Alarmknopf> alarmknoepfe) {
    return StreamSupport.stream(alarmknoepfe.spliterator(), false)
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  private AlarmknopfDto convertToDto(Alarmknopf alarmknopf) {
    return modelMapper.map(alarmknopf, AlarmknopfDto.class);
  }

  private Alarmknopf convertToEntity(AlarmknopfDto alarmknopfDto) {
    return modelMapper.map(alarmknopfDto, Alarmknopf.class);
  }
}
