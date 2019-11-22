package de.th.koeln.archilab.fae.faeteam4service.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import de.th.koeln.archilab.fae.faeteam4service.entities.position.Position;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
public class DementiellErkranktePerson {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String trackerId;

    @Embedded
    @Getter
    @Setter
    private Position position;

    public AlarmknopfHilferuf createAlarmknopfHilferuf(){
        return new AlarmknopfHilferuf(id);
    }
}
