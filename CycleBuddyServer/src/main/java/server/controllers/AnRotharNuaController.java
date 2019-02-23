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
    // In dev environment, call the following URL to get ALL bike stations (Cork, Galway & Limerick):
    // http://localhost:8090/an_rothar_nua/stations?schemeID=-1
    // schemeID values of 2, 3, or 4 are valid for individual city schemes
    @RequestMapping("/an_rothar_nua/stations")
    public int getStations(@RequestParam(value = "schemeID") int schemeID) throws IllegalStateException, IOException, SQLException
    {
        // First check that we have received a valid scheme ID, i.e. Cork, Galway, Limerick, or all three (-1)
        if (schemeID < -1 || schemeID > 4 || schemeID == 0)
        {
            throw new IllegalArgumentException(schemeID + " is not a valid An Rothar Nua scheme ID");
        }
        
        // Get data for all stations in the chosen scheme (or all schemes, if schemeID -1 was passed as an argument)
        ARNScheme response = ARNClient.getSchemeByID(schemeID, Application.config.getProperty("APIKey_AnRotharNua"));
        
        // Try to insert the retrieved data into the database
        try 
        {
        DatabaseUpdater dbupdater = new DatabaseUpdater();
        return dbupdater.insertAnRotharNuaStations(response.data);
        }
        
        catch(SQLException e)
        {
            throw e;
        }       
    }
}
