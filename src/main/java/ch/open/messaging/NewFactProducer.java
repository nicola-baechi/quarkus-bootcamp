package ch.open.messaging;

import ch.open.dto.NewFact;
import io.quarkus.scheduler.Scheduled;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.json.JSONObject;

@ApplicationScoped
public class NewFactProducer {

  @Inject
  @Channel("facts-out")
  Emitter<NewFact> factsEmitter;

  @Scheduled(every = "120s", delay = 5, delayUnit = TimeUnit.SECONDS)
  public void produceFact() throws IOException {
      factsEmitter.send(getRandomFact());
  }

  private NewFact getRandomFact() throws IOException {
    URL url = new URL("https://uselessfacts.jsph.pl/random.json?language=en");

    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    String inline = "";
    Scanner scanner = new Scanner(url.openStream());

    //Write all the JSON data into a string using a scanner
    while (scanner.hasNext()) {
      inline += scanner.nextLine();
    }

    //Close the scanner
    scanner.close();

    JSONObject json = new JSONObject(inline);
    String statement = json.getString("text");

    return new NewFact(statement);
  }


}
