package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePerson;
import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePersonRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class DementiellErkranktePersonenService {

  private final DementiellErkranktePersonRepository dementiellErkranktePersonRepository;

  public DementiellErkranktePersonenService(
      DementiellErkranktePersonRepository dementiellErkranktePersonRepository) {
    this.dementiellErkranktePersonRepository = dementiellErkranktePersonRepository;
  }

  public List<DementiellErkranktePerson> getPersonenInProximityOf(
      final Alarmknopf alarmknopf, final double radiusInMeters) {
    Iterable<DementiellErkranktePerson> allPersonen = dementiellErkranktePersonRepository.findAll();
    return StreamSupport.stream(allPersonen.spliterator(), false)
        .filter(
            person -> person.isInProximityOfPosition(alarmknopf.getPosition(), radiusInMeters))
        .collect(Collectors.toList());
  }
}
