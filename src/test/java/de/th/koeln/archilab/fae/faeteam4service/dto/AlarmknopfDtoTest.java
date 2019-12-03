package de.th.koeln.archilab.fae.faeteam4service.dto;

import static org.junit.Assert.assertEquals;

import de.th.koeln.archilab.fae.faeteam4service.dto.position.BreitengradDto;
import de.th.koeln.archilab.fae.faeteam4service.dto.position.LaengengradDto;
import de.th.koeln.archilab.fae.faeteam4service.dto.position.PositionDto;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class AlarmknopfDtoTest {

  private ModelMapper modelMapper = new ModelMapper();
  private final double toleratedDelta = 0.00000001;

  @Test
  public void whenConvertAlarmknopfEntityToPostDto_thenCorrect() {
    Position position =
        getPositionFromLatitudeAndLongitude(50.9432138, 6.9583567);

    Alarmknopf alarmknopf = new Alarmknopf();
    alarmknopf.setId("1234Id");
    alarmknopf.setName("myName");
    alarmknopf.setPosition(position);

    AlarmknopfDto alarmknopfDto = modelMapper.map(alarmknopf, AlarmknopfDto.class);

    double alarmknopfBreitengrad = alarmknopf.getPosition().getBreitengrad().getBreitengradDezimal();
    double alarmknopfLaengengrad = alarmknopf.getPosition().getLaengengrad().getLaengengradDezimal();
    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad().getBreitengradDezimal();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad().getLaengengradDezimal();

    assertEquals(alarmknopf.getId(), alarmknopfDto.getId());
    assertEquals(alarmknopf.getName(), alarmknopfDto.getName());
    assertEquals(alarmknopfBreitengrad, alarmknopfDtoBreitengrad, toleratedDelta);
    assertEquals(alarmknopfLaengengrad, alarmknopfDtoLaengengrad, toleratedDelta);
  }

  @Test
  public void whenConvertAlarmknopfDtoToAlarmknopfEntity_thenCorrect() {
    PositionDto positionDto =
        getPositionDtoFromLatitudeAndLongitude(50.9432138, 6.9583567);

    AlarmknopfDto alarmknopfDto = new AlarmknopfDto();
    alarmknopfDto.setId("1234Id");
    alarmknopfDto.setName("myName");
    alarmknopfDto.setPosition(positionDto);

    Alarmknopf alarmknopf = modelMapper.map(alarmknopfDto, Alarmknopf.class);

    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad().getBreitengradDezimal();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad().getLaengengradDezimal();
    double alarmknopfBreitengrad = alarmknopf.getPosition().getBreitengrad().getBreitengradDezimal();
    double alarmknopfLaengengrad = alarmknopf.getPosition().getLaengengrad().getLaengengradDezimal();

    assertEquals(alarmknopfDto.getId(), alarmknopf.getId());
    assertEquals(alarmknopfDto.getName(), alarmknopf.getName());
    assertEquals(alarmknopfDtoBreitengrad, alarmknopfBreitengrad, toleratedDelta);
    assertEquals(alarmknopfDtoLaengengrad, alarmknopfLaengengrad, toleratedDelta);
  }

  private Position getPositionFromLatitudeAndLongitude(final double latitude,
      final double longitude) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengradDezimal(latitude);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengradDezimal(longitude);

    return new Position(breitengrad, laengengrad);
  }

  private PositionDto getPositionDtoFromLatitudeAndLongitude(final double latitude,
      final double longitude) {
    BreitengradDto breitengrad = new BreitengradDto();
    breitengrad.setBreitengradDezimal(latitude);

    LaengengradDto laengengrad = new LaengengradDto();
    laengengrad.setLaengengradDezimal(longitude);

    return new PositionDto(breitengrad, laengengrad);
  }
}
