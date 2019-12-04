package de.th.koeln.archilab.fae.faeteam4service.position.persistence;

import de.th.koeln.archilab.fae.faeteam4service.position.persistence.Laengengrad;
import org.junit.Test;

public class LaengengradTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsOver85() {
    Laengengrad laengengrad = new Laengengrad(86.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenGradIsUnderMinus85() {
    Laengengrad laengengrad = new Laengengrad(-86.0);
  }
}
