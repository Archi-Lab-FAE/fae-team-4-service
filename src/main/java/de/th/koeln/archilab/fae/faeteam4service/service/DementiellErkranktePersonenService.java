package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePersonRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DementiellErkranktePersonenService {

  private final DementiellErkranktePersonRepository dementiellErkranktePersonRepository;

  public DementiellErkranktePersonenService(
      DementiellErkranktePersonRepository dementiellErkranktePersonRepository) {
    this.dementiellErkranktePersonRepository = dementiellErkranktePersonRepository;
  }

  public List<DementiellErkranktePerson> getPersonenInProximityOf(final Alarmknopf alarmknopf,
      final double radiusInMeters) {
    Iterable<DementiellErkranktePerson> allPersonen = dementiellErkranktePersonRepository.findAll();
    List<DementiellErkranktePerson> personenInProximity = new ArrayList<>();

    for (DementiellErkranktePerson dementiellErkranktePerson : allPersonen) {
      DistanceInMeters distanceInMeters = getDistanceInMeters(alarmknopf,
          dementiellErkranktePerson);
      if (distanceInMeters.getDistance() <= radiusInMeters) {
        personenInProximity.add(dementiellErkranktePerson);
      }
    }

    return personenInProximity;
  }

  private DistanceInMeters getDistanceInMeters(Alarmknopf alarmknopf,
      DementiellErkranktePerson dementiellErkranktePerson) {
    return alarmknopf.getPosition().getDistanceInMetersTo(dementiellErkranktePerson.getPosition());
  }
}
