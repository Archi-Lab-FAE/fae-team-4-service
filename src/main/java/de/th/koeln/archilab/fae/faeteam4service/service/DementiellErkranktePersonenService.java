package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DementiellErkranktePersonenService {

  private final DementiellErkranktePersonRepository dementiellErkranktePersonRepository;

  public DementiellErkranktePersonenService(
      DementiellErkranktePersonRepository dementiellErkranktePersonRepository) {
    this.dementiellErkranktePersonRepository = dementiellErkranktePersonRepository;
  }

  List<DementiellErkranktePerson> getPersonenInProximityOf(
      final Alarmknopf alarmknopf, final double radiusInMeters) {
    Iterable<DementiellErkranktePerson> allPersonen = dementiellErkranktePersonRepository.findAll();
    return StreamSupport.stream(allPersonen.spliterator(), false)
        .filter(
            (person) -> person.isInProximityOfPosition(alarmknopf.getPosition(), radiusInMeters))
        .collect(Collectors.toList());
  }
}
