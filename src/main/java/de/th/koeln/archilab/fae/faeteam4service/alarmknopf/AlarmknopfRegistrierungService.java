package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import java.util.List;
import java.util.Optional;

public interface AlarmknopfRegistrierungService {

  List<Alarmknopf> findAll();

  boolean save(Alarmknopf alarmknopf);

  Optional<Alarmknopf> findById(String alarmknopfId);

  boolean deleteById(String alarmknopfId);
}
