package de.th.koeln.archilab.fae.faeteam4service.service;

import de.th.koeln.archilab.fae.faeteam4service.DementiellErkranktePersonenService;
import de.th.koeln.archilab.fae.faeteam4service.entities.Alarmknopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfHilferuf;
import de.th.koeln.archilab.fae.faeteam4service.entities.AlarmknopfRepository;
import de.th.koeln.archilab.fae.faeteam4service.entities.DementiellErkranktePerson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnopfdruckService {

    private final AlarmknopfRepository alarmknopfRepository;
    private final DementiellErkranktePersonenService dementiellErkranktePersonenService;

    public KnopfdruckService(final AlarmknopfRepository alarmknopfRepository,
                             final DementiellErkranktePersonenService dementiellErkranktePersonenService) {
        this.alarmknopfRepository = alarmknopfRepository;
        this.dementiellErkranktePersonenService = dementiellErkranktePersonenService;
    }

    /**
     * Handels a Knopfdruck and created Hilferufe if there are DementiellErkranktePersonen nearby the Alarmknopf.
     *
     * @param alarmKnopfId KnopfId
     */
    void handleAlarmknopfdruck(final String alarmKnopfId) {
        Alarmknopf pressedAlarmknopf = alarmknopfRepository.findById(alarmKnopfId)
                .orElseThrow(() -> new IllegalArgumentException("HilfeKnopf not found"));

        List<DementiellErkranktePerson> potentiellBetroffeneDementiellErkranktePersonen =
                getPersonenInKnopfProximity(pressedAlarmknopf);

        List createdHilferufe = potentiellBetroffeneDementiellErkranktePersonen.stream()
                .map(DementiellErkranktePerson::createAlarmknopfHilferuf)
                .collect(Collectors.toList());

        //TODO: publish createdHilferufe here
    }


    private List<DementiellErkranktePerson> getPersonenInKnopfProximity(final Alarmknopf alarmknopf) {
        double radiusInMeters = 5;
        return dementiellErkranktePersonenService.getPersonenInProximityOf(alarmknopf, radiusInMeters);
    }
}
