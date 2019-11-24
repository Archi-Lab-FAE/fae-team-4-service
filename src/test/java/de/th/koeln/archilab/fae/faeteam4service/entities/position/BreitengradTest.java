package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import org.junit.Test;

public class BreitengradTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsOver180() {
    Breitengrad breitengrad = new Breitengrad(200.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsUnder0() {
    Breitengrad breitengrad = new Breitengrad(-1.0);
  }
}
