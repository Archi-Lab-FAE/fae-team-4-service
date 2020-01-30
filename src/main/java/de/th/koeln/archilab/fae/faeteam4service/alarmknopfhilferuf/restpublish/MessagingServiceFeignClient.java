package de.th.koeln.archilab.fae.faeteam4service.alarmknopfhilferuf.restpublish;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "https://api.fae.archi-lab.io/team-3/ausnahmesituation")
public interface MessagingServiceFeignClient {

  @RequestMapping(value = "/team-3/ausnahmesituation", method = RequestMethod.POST)
  Ausnahmesituation createAusnahmesituation(Ausnahmesituation ausnahmesituation);
}
