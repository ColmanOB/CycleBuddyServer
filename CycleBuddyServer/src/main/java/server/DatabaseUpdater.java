package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data_models.ARNScheme;
import data_models.JCDStation;
import data_models.NBCity;

public class DatabaseUpdater
{
    final String url = "jdbc:postgresql://localhost:5432/BikeBuddy?currentSchema=public";
    final String user = Application.config.getProperty("DBuser");
    final String password = Application.config.getProperty("DBpassword");

    private Connection connectToDatabase() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }
    
    /**
     * Updates the record in the database for each JC Decaux station passed in.
     * 
     * If a matching record does not exist for any individual station, one will be inserted.
     * 
     * @param stations An array of JCDStation objects
     * @return A count of the rows updated 
     * @throws SQLException In the event of a database access error
     */
    public int updateJCDecauxStations(JCDStation[] stations) throws SQLException
    {
        String sql = "INSERT INTO public.jcdecaux_stations" 
                + "(number, name, address, position, banking, bonus, status, contract_name, bike_stands, available_bike_stands, available_bikes, last_update) "
                + " VALUES (?,?,?,ST_SetSRID(ST_MakePoint(?, ?), 2100),?,?,?,?,?,?,?,to_timestamp(?))"
                + " ON CONFLICT ON CONSTRAINT jcdecaux_stations_pk"
                + " DO UPDATE"
                    + " SET banking = EXCLUDED.banking,"
                    + " bonus = EXCLUDED.bonus,"
                    + " status = EXCLUDED.status,"
                    + " bike_stands = EXCLUDED.bike_stands,"
                    + " available_bike_stands = EXCLUDED.available_bike_stands,"
                    + " available_bikes = EXCLUDED.available_bikes,"
                    + " last_update = EXCLUDED.last_update;";

        Connection dbConnection = connectToDatabase();
        dbConnection.setAutoCommit(false);
        PreparedStatement ps = dbConnection.prepareStatement(sql);
        
        try
        {
            for (JCDStation station : stations)
            {
                ps.setInt(1, station.number);
                ps.setString(2, station.name);
                ps.setString(3, station.address);
                ps.setDouble(4, station.position.lng);
                ps.setDouble(5, station.position.lat);
                ps.setBoolean(6, station.banking);
                ps.setBoolean(7, station.bonus);
                ps.setString(8, station.status);
                ps.setString(9, station.contract_name);
                ps.setInt(10, station.bike_stands);
                ps.setInt(11, station.available_bike_stands);
                ps.setInt(12, station.available_bikes);
                ps.setDouble(13, (station.last_update / 1000));
    
                ps.addBatch();
            }
            
        // Commit the batch, and return a count of affected rows    
        int rowCount = ps.executeBatch().length;
        dbConnection.commit();
        return rowCount;
        }
        
        catch (Exception e)
        {
            dbConnection.rollback();
            throw e;
        }
        
        finally 
        {
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
    }
    
    /**
     * Updates the record in the database for each An Rothar Nua station passed in.
     * 
     * If a matching record does not exist for any individual station, one will be inserted.
     * 
     * @param stations the contents of the Data array in a response from the An Rothar Nua API
     * @return the count of rows affected
     * @throws SQLException in the event of a database access error
     */
    public int updateAnRotharNuaStations(ARNScheme.Data[] stations) throws SQLException
    {
        // Prepare the SQL, including converting the latitude and longitude into a 'point'
        // and converting the date/time retrieved from the An Rothar Nua API into a PostgreSQL timestamp
        String sql = "INSERT INTO public.an_rothar_nua_stations" 
                + "(scheme_id, scheme_short_name, station_id, name, name_irish, docks_count, bikes_available, docks_available, status, position, date_status) "
                + " VALUES (?,?,?,?,?,?,?,?,?,ST_SetSRID(ST_MakePoint(?, ?), 2100),to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'))"
                + " ON CONFLICT ON CONSTRAINT an_rothar_nua_stations_pk"
                + " DO UPDATE"
                    + " SET docks_count = EXCLUDED.docks_count,"
                    + " bikes_available = EXCLUDED.bikes_available,"
                    + " docks_available = EXCLUDED.docks_available,"
                    + " status = EXCLUDED.status,"
                    + " date_status = EXCLUDED.date_status;";

        // We run the updates as a batch, so either all updates succeed, or we roll back
        Connection dbConnection = connectToDatabase();
        dbConnection.setAutoCommit(false);
        PreparedStatement ps = dbConnection.prepareStatement(sql);

        // Use the values retrieved from the An Rothar Nua API 
        // to populate the SQL insert with the data for each individual station
        try
        {
            for (ARNScheme.Data station : stations)
            {
                ps.setInt(1, station.schemeId);
                ps.setString(2, station.schemeShortName);
                ps.setInt(3, station.stationId);
                ps.setString(4, station.name);
                ps.setString(5, station.nameIrish);
                ps.setInt(6, station.docksCount);
                ps.setInt(7, station.bikesAvailable);
                ps.setInt(8, station.docksAvailable);
                ps.setInt(9, station.status);
                ps.setDouble(11, station.latitude);
                ps.setDouble(10, station.longitude);
                ps.setString(12, station.dateStatus);
                
                ps.addBatch();
            }
            // If no exception was encountered, commit the batch 
            // and return a count of affected rows    
            int rowCount = ps.executeBatch().length;
            dbConnection.commit();
            return rowCount;
        }
        
        catch (Exception e)
        {
            // If anything went wrong, undo the whole batch
            dbConnection.rollback();
            throw e;
        }
        
        finally 
        {
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
    }
    
    /**
     * Updates the record in the database for each NextBike station passed in.
     * 
     * If a matching record does not exist for any individual station, one will be inserted.
     * 
     * @param stations An array of JCDStation objects
     * @return A count of the rows updated 
     * @throws SQLException In the event of a database access error
     */
    public int updateNextBikeStations(NBCity.Country.City.Place[] stations) throws SQLException
    {
        String sql = "INSERT INTO public.next_bike_stations" 
                + "(uid, position, name, address, spot, number, bikes, bike_racks, free_racks, maintenance, terminal_type, place_type, rack_locks) "
                + " VALUES (?,ST_SetSRID(ST_MakePoint(?,?), 2100),?,?,?,?,?,?,?,?,?,?,?)"
                + " ON CONFLICT ON CONSTRAINT next_bike_stations_pk"
                + " DO UPDATE"
                    + " SET bikes = EXCLUDED.bikes,"
                    + " bike_racks = EXCLUDED.bike_racks,"
                    + " free_racks = EXCLUDED.free_racks,"
                    + " maintenance = EXCLUDED.maintenance,"
                    + " terminal_type = EXCLUDED.terminal_type,"
                    + " place_type = EXCLUDED.place_type,"
                    + " rack_locks = EXCLUDED.rack_locks;";

        Connection dbConnection = connectToDatabase();
        dbConnection.setAutoCommit(false);
        PreparedStatement ps = dbConnection.prepareStatement(sql);
        
        try
        {
            for /*(NBCity.Country.City.Place station : stations) */ (int i = 0; i < stations.length; i++)
            {
                ps.setInt(1, stations[i].uid);
                ps.setDouble(2, stations[i].lng);
                ps.setDouble(3, stations[i].lat);
                ps.setString(4, stations[i].name);
                ps.setString(5, stations[i].address);
                ps.setBoolean(6, stations[i].spot);
                ps.setInt(7, stations[i].number);
                ps.setInt(8, stations[i].bikes);
                ps.setInt(9, stations[i].bikeRacks);
                ps.setInt(10, stations[i].freeRacks);
                ps.setBoolean(11, stations[i].maintenance);
                ps.setString(12, stations[i].terminalType);
                ps.setInt(13, stations[i].placeType);
                ps.setBoolean(14, stations[i].rackLocks);
                    
                ps.addBatch();
            }
    
        // Commit the batch, and return a count of affected rows    
        int rowCount = ps.executeBatch().length;
        dbConnection.commit();
        return rowCount;
        }
        
        catch (Exception e)
        {
            dbConnection.rollback();
            throw e;
        }
        
        finally 
        {
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
    }
}
