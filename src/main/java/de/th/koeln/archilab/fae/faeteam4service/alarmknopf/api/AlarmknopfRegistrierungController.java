package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfRegistrierungServiceImpl;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

  @Operation(summary = "Alle Alarmknöpfe als Liste")
  @GetMapping(path = "/alarmknoepfe", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<AlarmknopfDto> getAlarmknoepfe() {
    List<Alarmknopf> alarmknoepfe = alarmknopfRegistrierungServiceImpl.findAll();
    return getAlarmknoepfeDto(alarmknoepfe);
  }

  @Operation(summary = "Informationen zu Alarmknopf dessen ID übermittelt wird")
  @GetMapping(path = "/alarmknoepfe/{alarmknopfId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AlarmknopfDto> getAlarmknopf(@PathVariable String alarmknopfId) {
    Optional<Alarmknopf> alarmknopf = alarmknopfRegistrierungServiceImpl.findById(alarmknopfId);

    return alarmknopf
        .map(value -> new ResponseEntity<>(convertToDto(value), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @Operation(summary = "Registrierung eines Alarmknopfes")
  @PutMapping(path = "/alarmknoepfe/{alarmknopfId}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity registerAlarmknopf(@PathVariable String alarmknopfId,
      @RequestBody AlarmknopfDto alarmknopfDto) {
    Alarmknopf alarmknopf = convertToEntity(alarmknopfId, alarmknopfDto);
    alarmknopfRegistrierungServiceImpl.save(alarmknopf);

    return new ResponseEntity<>(alarmknopfDto, HttpStatus.OK);
  }

  @Operation(summary = "Löschen des Alarmknopfs dessen ID übermittelt wird")
  @DeleteMapping(path = "/alarmknoepfe/{alarmknopfId}")
  public ResponseEntity deleteAlarmknopf(@PathVariable String alarmknopfId) {
    boolean wasDeletionSuccessful = alarmknopfRegistrierungServiceImpl.deleteById(alarmknopfId);

    if (wasDeletionSuccessful) {
      return new ResponseEntity(HttpStatus.OK);
    }
    return new ResponseEntity(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<AlarmknopfDto> handle(final Exception ex) {
    if (ex instanceof MappingException || ex instanceof HttpMessageNotReadableException) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  private List<AlarmknopfDto> getAlarmknoepfeDto(final Iterable<Alarmknopf> alarmknoepfe) {
    return StreamSupport.stream(alarmknoepfe.spliterator(), false)
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  private AlarmknopfDto convertToDto(final Alarmknopf alarmknopf) {
    return modelMapper.map(alarmknopf, AlarmknopfDto.class);
  }

  private Alarmknopf convertToEntity(final String alarmknopfId, final AlarmknopfDto alarmknopfDto) {
    alarmknopfDto.setId(alarmknopfId);
    return modelMapper.map(alarmknopfDto, Alarmknopf.class);
  }
}
