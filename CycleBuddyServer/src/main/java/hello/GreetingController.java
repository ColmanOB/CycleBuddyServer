package hello;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jc_decaux_client.Station;
import jc_decaux_client.StationRetriever;

@RestController
public class GreetingController 
	{     
    @RequestMapping("/station")
    public Station station(@RequestParam(value="id") String id) throws IllegalStateException, IOException 
    	{
    	StationRetriever response = new StationRetriever();
    	return response.getStationByID(id);
    	}
}
