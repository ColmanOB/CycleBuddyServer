package server;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.JCDContract;
import data_models.JCDStation;
import api_client.JCDClient;

@RestController
public class JCDecauxController
{
    // In dev environment, call the following URL (example)
    // http://localhost:8090/jcdecaux/station?contractName=Dublin&stationId=2
    @RequestMapping("/jcdecaux/station")
    public JCDStation getStation(@RequestParam(value = "contractName") String contractName, @RequestParam(value = "stationId") int id) throws IllegalStateException, IOException
    {
        return JCDClient.getStationByID(contractName, id, Application.config.getProperty("APIKey_JCDecaux"));
    }
    
    // In dev environment, call the following URL to get Dublin stations
    // http://localhost:8090/jcdecaux/stations_in_contract?contractName=Dublin
    @RequestMapping("/jcdecaux/stations_in_contract")
    public int getStationsInContract(@RequestParam(value = "contractName") String contractName) throws IllegalStateException, IOException, SQLException
    {

        JCDStation[] stations = JCDClient.getStationListByContract(contractName, Application.config.getProperty("APIKey_JCDecaux"));
        
        try 
        {
            DatabaseUpdater dbupdater = new DatabaseUpdater();
            return dbupdater.updateJCDecauxStations(stations);
        }
        
        catch(SQLException e)
        {
            throw e;
        }
    }

    // In dev environment, call this using the following URL:
    // http://localhost:8090/jcdecaux/stations_all
    @RequestMapping("/jcdecaux/stations_all")
    public int getStationsAll() throws IllegalStateException, IOException, SQLException
    {
        JCDStation[] stations = JCDClient.getStationListAll(Application.config.getProperty("APIKey_JCDecaux"));
        
        try 
        {
            DatabaseUpdater dbupdater = new DatabaseUpdater();
            return dbupdater.updateJCDecauxStations(stations);
        }
        
        catch(SQLException e)
        {
            throw e;
        }
    }

    // In dev environment, call this using the following URL:   
    // http://localhost:8090/jcdecaux/contract_list
    @RequestMapping("/jcdecaux/contract_list")
    public JCDContract[] getContracts() throws IllegalStateException, IOException
    {
        return JCDClient.getContractList(Application.config.getProperty("APIKey_JCDecaux"));
    }
}
