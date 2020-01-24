package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api;

import static org.junit.Assert.assertEquals;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.common.Distance;
import de.th.koeln.archilab.fae.faeteam4service.config.ModelMapperConfig;
import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
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
  public void whenConvertAlarmknopfEntityToAlarmknopfDto_thenCorrect() {
    Position position =
        getPositionFromBreitengradAndLaengengrad(50.9432138, 6.9583567);

    Distance meldungsrelevanterRadius = new Distance(5.0);

    Alarmknopf alarmknopf = new Alarmknopf();
    alarmknopf.setId("1234Id");
    alarmknopf.setName("myName");
    alarmknopf.setPosition(position);
    alarmknopf.setMeldungsrelevanterRadius(meldungsrelevanterRadius);

    AlarmknopfDto alarmknopfDto = modelMapper.map(alarmknopf, AlarmknopfDto.class);

    assert alarmknopf.getPosition().getBreitengrad() != null;
    double alarmknopfBreitengrad = alarmknopf.getPosition().getBreitengrad()
        .getBreitengradDezimal();
    assert alarmknopf.getPosition().getLaengengrad() != null;
    double alarmknopfLaengengrad = alarmknopf.getPosition().getLaengengrad()
        .getLaengengradDezimal();
    double alarmknopfRadius = alarmknopf.getMeldungsrelevanterRadius().getDistanceInMeters();
    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad();
    double alarmknopfDtoRadius = alarmknopfDto.getMeldungsrelevanterRadiusInMetern();

    assertEquals(alarmknopf.getId(), alarmknopfDto.getId());
    assertEquals(alarmknopf.getName(), alarmknopfDto.getName());
    assertEquals(alarmknopfBreitengrad, alarmknopfDtoBreitengrad, toleratedDelta);
    assertEquals(alarmknopfLaengengrad, alarmknopfDtoLaengengrad, toleratedDelta);
    assertEquals(alarmknopfRadius, alarmknopfDtoRadius, toleratedDelta);
  }

  @Test
  public void whenConvertAlarmknopfDtoToAlarmknopfEntity_thenCorrect() {
    PositionDto positionDto = new PositionDto(50.9432138, 6.9583567);

    AlarmknopfDto alarmknopfDto = new AlarmknopfDto();
    alarmknopfDto.setId("1234Id");
    alarmknopfDto.setName("myName");
    alarmknopfDto.setPosition(positionDto);
    alarmknopfDto.setMeldungsrelevanterRadiusInMetern(32.413);

    Alarmknopf alarmknopf = modelMapper.map(alarmknopfDto, Alarmknopf.class);
    modelMapper.validate();

    double alarmknopfDtoBreitengrad = alarmknopfDto.getPosition().getBreitengrad();
    double alarmknopfDtoLaengengrad = alarmknopfDto.getPosition().getLaengengrad();
    double alarmknopfDtoRadius = alarmknopfDto.getMeldungsrelevanterRadiusInMetern();

    assert alarmknopf.getPosition().getBreitengrad() != null;
    double alarmknopfBreitengrad = alarmknopf.getPosition().getBreitengrad()
        .getBreitengradDezimal();
    assert alarmknopf.getPosition().getLaengengrad() != null;
    double alarmknopfLaengengrad = alarmknopf.getPosition().getLaengengrad()
        .getLaengengradDezimal();
    double alarmknopfRadius = alarmknopf.getMeldungsrelevanterRadius().getDistanceInMeters();

    assertEquals(alarmknopfDto.getId(), alarmknopf.getId());
    assertEquals(alarmknopfDto.getName(), alarmknopf.getName());
    assertEquals(alarmknopfDtoBreitengrad, alarmknopfBreitengrad, toleratedDelta);
    assertEquals(alarmknopfDtoLaengengrad, alarmknopfLaengengrad, toleratedDelta);
    assertEquals(alarmknopfDtoRadius, alarmknopfRadius, toleratedDelta);
  }

  private Position getPositionFromBreitengradAndLaengengrad(final double breitengradToSet,
      final double laengengradToSet) {
    Breitengrad breitengrad = new Breitengrad();
    breitengrad.setBreitengradDezimal(breitengradToSet);

    Laengengrad laengengrad = new Laengengrad();
    laengengrad.setLaengengradDezimal(laengengradToSet);

    return new Position(breitengrad, laengengrad);
  }
}
