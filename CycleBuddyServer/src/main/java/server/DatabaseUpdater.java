package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data_models.ARNScheme;
import data_models.JCDStation;

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
    
    public int insertAnRotharNuaStations(ARNScheme.Data[] stations) throws SQLException
    {
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

        Connection dbConnection = connectToDatabase();
        dbConnection.setAutoCommit(false);
        PreparedStatement ps = dbConnection.prepareStatement(sql);

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
            //return ps.executeBatch();
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
