package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import java.util.List;
import java.util.Optional;

public interface AlarmknopfRegistrierungService {

  List<Alarmknopf> findAll();

  void save(Alarmknopf alarmknopf);

  Optional<Alarmknopf> findById(String alarmknopfId);

  void deleteById(String alarmknopfId);
}
