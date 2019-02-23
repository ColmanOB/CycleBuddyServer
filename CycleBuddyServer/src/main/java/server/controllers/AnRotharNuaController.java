package server.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.ARNScheme;
import server.Application;
import server.DatabaseUpdater;
import api_client.ARNClient;

@RestController
public class AnRotharNuaController
{
    @RequestMapping("/an_rothar_nua/stations")
    public ARNScheme getStations(@RequestParam(value = "schemeID") int schemeID) throws IllegalStateException, IOException
    {
        ARNScheme response = ARNClient.getSchemeByID(schemeID, Application.config.getProperty("APIKey_AnRotharNua"));
        
        try 
        {
        DatabaseUpdater dbupdater = new DatabaseUpdater();
        dbupdater.insertAnRotharNuaStations(response.data);
        }
        
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return response;
        
    }
}
