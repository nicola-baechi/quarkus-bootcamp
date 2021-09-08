package ch.open.messaging;

import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.smallrye.common.annotation.Blocking;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class NewFactConsumer {

  @Inject
  FactService factService;

  @Incoming("facts-in")
  @Blocking
  public void consume(NewFact newFact){
    factService.addFact(newFact);
  }

}
