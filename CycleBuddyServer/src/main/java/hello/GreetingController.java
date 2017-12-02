package hello;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jc_decaux_client.Station;
import jc_decaux_client.StationRetriever;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /*@RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
     
    @RequestMapping("/station")
    public Station station(@RequestParam(value="id") String id) throws IllegalStateException, IOException {
      StationRetriever response = new StationRetriever();
    	return response.getStationByID(id);
    }
}
