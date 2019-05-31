package server;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.NBCity;
import api_client.NBClient;

@RestController
public class NextBikeController
{
    // In dev environment, call the following URL (example)
    // http://localhost:8090/nextbike/stations?city_id=238
    @RequestMapping("/nextbike/stations")
    public int getStations(@RequestParam(value = "city_id") int cityId) throws IllegalStateException, IOException, SQLException
    {
        NBCity response = NBClient.getCityByID(cityId);
        
        // Try to insert the retrieved data into the database
        try 
        {
        DatabaseUpdater dbupdater = new DatabaseUpdater();
        return dbupdater.updateNextBikeStations(response.countries[0].cities[0].places);
        }
        
        catch(SQLException e)
        {
            throw e;
        }    
    }
}
