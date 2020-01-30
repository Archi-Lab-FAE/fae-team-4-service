package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("fae-team-3-service")
public interface MessagingServiceFeignClient {

  @RequestMapping(value = "/ausnahmesituation", method = RequestMethod.POST)
  Ausnahmesituation createAusnahmeSituation(Ausnahmesituation ausnahmesituation);
}
