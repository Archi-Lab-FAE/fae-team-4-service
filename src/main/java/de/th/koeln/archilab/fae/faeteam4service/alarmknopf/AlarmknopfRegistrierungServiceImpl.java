package de.th.koeln.archilab.fae.faeteam4service.alarmknopf;

import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.alarmknopf.persistence.AlarmknopfRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AlarmknopfRegistrierungServiceImpl implements AlarmknopfRegistrierungService {


  private final AlarmknopfRepository alarmknopfRepository;

  public AlarmknopfRegistrierungServiceImpl(AlarmknopfRepository alarmknopfRepository) {
    this.alarmknopfRepository = alarmknopfRepository;

  }

  @Override
  public List<Alarmknopf> findAll() {
    return alarmknopfRepository.findAll();
  }

  @Override
  public void save(Alarmknopf alarmknopf) {
    alarmknopfRepository.save(alarmknopf);
  }

  @Override
  public Optional<Alarmknopf> findById(String alarmknopfId) {
    return alarmknopfRepository.findById(alarmknopfId);
  }

  @Override
  public boolean deleteById(String alarmknopfId) {

    Optional<Alarmknopf> foundAlarmknopf = alarmknopfRepository.findById(alarmknopfId);

    if (!foundAlarmknopf.isPresent()) {
      return false;
    }

    Alarmknopf alarmknopf = foundAlarmknopf.get();
    alarmknopfRepository.delete(alarmknopf);
    return true;
  }
}
