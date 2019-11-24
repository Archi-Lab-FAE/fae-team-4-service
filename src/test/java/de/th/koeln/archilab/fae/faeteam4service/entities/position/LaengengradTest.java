package de.th.koeln.archilab.fae.faeteam4service.entities.position;

import org.junit.Test;

public class LaengengradTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsOver180() {
    Laengengrad laengengrad = new Laengengrad(200.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsUnder0() {
    Laengengrad laengengrad = new Laengengrad(-1.0);
  }
}
