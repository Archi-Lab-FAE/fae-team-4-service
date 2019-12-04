package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import static org.junit.Assert.assertEquals;

import de.th.koeln.archilab.fae.faeteam4service.config.ModelMapperConfig;
import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Breitengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Laengengrad;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class AlarmknopfDtoTest {

  private final double toleratedDelta = 0.00000001;
  private ModelMapper modelMapper;

  @Before
  public void initModelMapper() {
    ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
    modelMapper = modelMapperConfig.modelMapper();
  }

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
    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad();

    assertEquals(alarmknopf.getId(), alarmknopfDto.getId());
    assertEquals(alarmknopf.getName(), alarmknopfDto.getName());
    assertEquals(alarmknopfBreitengrad, alarmknopfDtoBreitengrad, toleratedDelta);
    assertEquals(alarmknopfLaengengrad, alarmknopfDtoLaengengrad, toleratedDelta);
  }

  @Test
  public void whenConvertAlarmknopfDtoToAlarmknopfEntity_thenCorrect() {
    PositionDto positionDto = new PositionDto(50.9432138, 6.9583567);

    AlarmknopfDto alarmknopfDto = new AlarmknopfDto();
    alarmknopfDto.setId("1234Id");
    alarmknopfDto.setName("myName");
    alarmknopfDto.setPosition(positionDto);

    Alarmknopf alarmknopf = modelMapper.map(alarmknopfDto, Alarmknopf.class);

    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad();
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
}
