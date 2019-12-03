package de.th.koeln.archilab.fae.faeteam4service.controller;

import de.th.koeln.archilab.fae.faeteam4service.dto.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmknopfRegistrierungController {

  private final AlarmknopfRepository alarmknopfRepository;
  private final ModelMapper modelMapper;


  public AlarmknopfRegistrierungController(AlarmknopfRepository alarmknopfRepository,
      ModelMapper modelMapper) {
    this.alarmknopfRepository = alarmknopfRepository;
    this.modelMapper = modelMapper;
  }

  @GetMapping(path = "/alarmknoepfe", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<AlarmknopfDto> getAlarmknoepfe() {
    Iterable<Alarmknopf> alarmknoepfe = alarmknopfRepository.findAll();
    return getAlarmknoepfeDto(alarmknoepfe);
  }

  @GetMapping(path = "/alarmknoepfe/{alarmknopfId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public AlarmknopfDto getAlarmknopf(@PathVariable String alarmknopfId) {
    Optional<Alarmknopf> alarmknopf = alarmknopfRepository.findById(alarmknopfId);

    if (!alarmknopf.isPresent()) {
      throw new IllegalArgumentException("Alarmknopf not found");
    }

    return convertToDto(alarmknopf.get());
  }

  @PostMapping(path = "/alarmknoepfe/",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Alarmknopf registerAlarmknopf(@RequestBody AlarmknopfDto newAlarmknopfDto) {
    Alarmknopf alarmknopf = convertToEntity(newAlarmknopfDto);
    return alarmknopfRepository.save(alarmknopf);
  }

  @DeleteMapping(path = "/alarmknoepfe/{trackerId}")
  public void deleteOrder(@PathVariable String trackerId) {
    alarmknopfRepository.deleteById(trackerId);
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
