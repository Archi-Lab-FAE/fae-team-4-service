package de.th.koeln.archilab.fae.faeteam4service.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmknopfRepository extends CrudRepository<Alarmknopf, String> {

}