package de.th.koeln.archilab.fae.faeteam4service.config;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.api.AlarmknopfDto;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.common.Distance;
import de.th.koeln.archilab.fae.faeteam4service.position.api.PositionDto;
import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Position;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  private ModelMapper modelMapper;

  private static final double FALLBACK_RADIUS = 0.0;
  private static final double FALLBACK_BREITENGRAD = 0.0;
  private static final double FALLBACK_LAENGENGRAD = 0.0;

  @Bean
  public ModelMapper modelMapper() {
    modelMapper = new ModelMapper();

    alarmknopfToDtoMapping();
    dtoToAlarmknopfMapping();

    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    return this.modelMapper;
  }

  private void dtoToAlarmknopfMapping() {
    Converter<PositionDto, Position> positionDtoConverter =
        mappingContext -> {
          double latitude = mappingContext.getSource().getBreitengrad();
          double longitude = mappingContext.getSource().getLaengengrad();
          return new Position(latitude, longitude);
        };

    Converter<Double, Distance> raidusDtoConverter =
        mappingContext -> {
          double radius = mappingContext.getSource();
          return new Distance(radius);
        };

    PropertyMap<AlarmknopfDto, Alarmknopf> alarmknopfDtoMapping =
        new PropertyMap<AlarmknopfDto, Alarmknopf>() {
          @Override
          protected void configure() {
            using(positionDtoConverter)
                .map(source.getPosition())
                .setPosition(new Position(FALLBACK_BREITENGRAD, FALLBACK_LAENGENGRAD));
            using(raidusDtoConverter)
                .map(source.getMeldungsrelevanterRadiusInMetern())
                .setMeldungsrelevanterRadius(new Distance(FALLBACK_RADIUS));
          }
        };
    modelMapper.addMappings(alarmknopfDtoMapping);
  }

  private void alarmknopfToDtoMapping() {
    Converter<Position, PositionDto> positionConverter =
        mappingContext -> {
          double latitude = mappingContext.getSource().getBreitengrad().getBreitengradDezimal();
          double longitude = mappingContext.getSource().getLaengengrad().getLaengengradDezimal();
          return new PositionDto(latitude, longitude);
        };

    PropertyMap<Alarmknopf, AlarmknopfDto> alarmknopfMapping =
        new PropertyMap<Alarmknopf, AlarmknopfDto>() {
          @Override
          protected void configure() {
            using(positionConverter)
                .map(source.getPosition())
                .setPosition(new PositionDto(FALLBACK_BREITENGRAD, FALLBACK_LAENGENGRAD));
            map(source.getMeldungsrelevanterRadius().getDistanceInMeters())
                .setMeldungsrelevanterRadiusInMetern(FALLBACK_RADIUS);
          }
        };
    modelMapper.addMappings(alarmknopfMapping);
  }
}
