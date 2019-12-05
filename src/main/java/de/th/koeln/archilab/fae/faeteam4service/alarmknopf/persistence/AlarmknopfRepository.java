package de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmknopfRepository extends JpaRepository<Alarmknopf, String> {

}
