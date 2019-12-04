package de.th.koeln.archilab.fae.faeteam4service.position.persistence;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Breitengrad;
import org.junit.Test;

public class BreitengradTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsOver180() {
    Breitengrad breitengrad = new Breitengrad(200.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsUnderMinus180() {
    Breitengrad breitengrad = new Breitengrad(-181.0);
  }
}
