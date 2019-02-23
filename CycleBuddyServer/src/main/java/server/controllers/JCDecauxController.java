package server.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.JCDContract;
import data_models.JCDStation;
import server.Application;
import server.DatabaseUpdater;
import api_client.JCDClient;

@RestController
public class JCDecauxController
{
    // In dev environment, call the following URL (example)
    // http://localhost:8090/jcdecaux/station?contractName=Dublin&stationId=2
    // Needs to be refactored to query database instead of making an API call
    @RequestMapping("/jcdecaux/station")
    public JCDStation getStation(@RequestParam(value = "contractName") String contractName, @RequestParam(value = "stationId") int id) throws IllegalStateException, IOException
    {
        return JCDClient.getStationByID(contractName, id, Application.config.getProperty("APIKey_JCDecaux"));
    }
    
    // In dev environment, call the following URL to get Dublin stations
    // http://localhost:8090/jcdecaux/stations_in_contract?contractName=Dublin
    // Currently you need to delete the contents of the jcdecaux_stations DB table, as this currently does a SQL INSERT behind the scenes
    @RequestMapping("/jcdecaux/stations_in_contract")
    public JCDStation[] getStationsInContract(@RequestParam(value = "contractName") String contractName) throws IllegalStateException, IOException, SQLException
    {

        JCDStation[] stations = JCDClient.getStationListByContract(contractName, Application.config.getProperty("APIKey_JCDecaux"));
        try 
        {
        DatabaseUpdater dbupdater = new DatabaseUpdater();
        dbupdater.insertJCDecauxStations(stations);
        }
        
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return stations;
    }

    // In dev environment, call this using the following URL:
    // http://localhost:8090/jcdecaux/stations_all
    @RequestMapping("/jcdecaux/stations_all")
    public JCDStation[] getStationsAll() throws IllegalStateException, IOException
    {
        return JCDClient.getStationListAll(Application.config.getProperty("APIKey_JCDecaux"));
    }

    // In dev environment, call this using the following URL:   
    // http://localhost:8090/jcdecaux/contract_list
    @RequestMapping("/jcdecaux/contract_list")
    public JCDContract[] getContracts() throws IllegalStateException, IOException
    {
        return JCDClient.getContractList(Application.config.getProperty("APIKey_JCDecaux"));
    }
}
