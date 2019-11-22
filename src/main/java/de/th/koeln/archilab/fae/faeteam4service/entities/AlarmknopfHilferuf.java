package de.th.koeln.archilab.fae.faeteam4service.entities;

import lombok.Getter;

public class AlarmknopfHilferuf {

    @Getter
    private String dementiellErkranktePersonId;

    public AlarmknopfHilferuf(String dementiellErkranktePersonId) {
        this.dementiellErkranktePersonId = dementiellErkranktePersonId;
    }
}
