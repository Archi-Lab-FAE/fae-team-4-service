package de.th.koeln.archilab.fae.faeteam4service.hilfeknopf.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HilfeknopfRepository extends CrudRepository<HilfeKnopf, HilfeknopfId> {

}
