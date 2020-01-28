package de.th.koeln.archilab.fae.faeteam4service.errorhandling;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, String> {}
