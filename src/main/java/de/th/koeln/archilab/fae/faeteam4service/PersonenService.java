package de.th.koeln.archilab.fae.faeteam4service;

import de.th.koeln.archilab.fae.faeteam4service.domain.DistanceInMeters;
import de.th.koeln.archilab.fae.faeteam4service.entities.Knopf;
import de.th.koeln.archilab.fae.faeteam4service.entities.Person;
import de.th.koeln.archilab.fae.faeteam4service.entities.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PersonenService {

  private final PersonRepository personRepository;

  public PersonenService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public List<Person> getPersonenInProximityOf(final Knopf knopf, final double radiusInMeters) {
    Iterable<Person> allPersonen = personRepository.findAll();
    List<Person> personenInProximity = new ArrayList<>();

    for (Person person : allPersonen) {
      DistanceInMeters distanceInMeters = getDistanceInMeters(knopf, person);
      if (distanceInMeters.getDistance() <= radiusInMeters) {
        personenInProximity.add(person);
      }
    }

    return personenInProximity;
  }

  private DistanceInMeters getDistanceInMeters(Knopf knopf, Person person) {
    return knopf.getPosition().getDistanceInMetersTo(person.getPosition());
  }
}
