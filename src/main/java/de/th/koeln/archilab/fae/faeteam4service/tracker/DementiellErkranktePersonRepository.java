package de.th.koeln.archilab.fae.faeteam4service.tracker;

import de.th.koeln.archilab.fae.faeteam4service.tracker.DementiellErkranktePerson;
import org.springframework.data.repository.CrudRepository;

public interface DementiellErkranktePersonRepository extends
    CrudRepository<DementiellErkranktePerson, String> {

}