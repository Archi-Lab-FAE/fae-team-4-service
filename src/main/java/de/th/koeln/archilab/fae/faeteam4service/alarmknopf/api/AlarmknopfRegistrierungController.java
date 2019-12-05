package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.AlarmknopfRegistrierungServiceImpl;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public Iterable<AlarmknopfDto> getAlarmknoepfe() {
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
  public AlarmknopfDto registerAlarmknopf(@RequestBody AlarmknopfDto newAlarmknopfDto) {
    Alarmknopf alarmknopf = convertToEntity(newAlarmknopfDto);
    alarmknopfRegistrierungServiceImpl.save(alarmknopf);
    return newAlarmknopfDto;
  }

  @DeleteMapping(path = "/alarmknoepfe/{alarmknopfId}")
  public void deleteOrder(@PathVariable String alarmknopfId) {
    alarmknopfRegistrierungServiceImpl.deleteById(alarmknopfId);
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
