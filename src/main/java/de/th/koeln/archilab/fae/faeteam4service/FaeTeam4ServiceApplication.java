package de.th.koeln.archilab.fae.faeteam4service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FaeTeam4ServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(FaeTeam4ServiceApplication.class, args);
  }
}
