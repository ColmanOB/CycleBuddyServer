package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    // Currently tries indiscriminately insert all stations in a specified contract
    // It falls over if any of the stations are already in the database
    // It probably should return something useful instead of void, like a count of inserted rows etc.
    // It will probably be replaced entirely with an 'UPSERT' type statment
    public void insertJCDecauxStations(JCDStation[] stations) throws SQLException
    {
        String sql = "INSERT INTO public.jcdecaux_stations (number, name, address, position, banking, bonus, status, contract_name, bike_stands, available_bike_stands, available_bikes, last_update) "
                + "VALUES (?,?,?,ST_SetSRID(ST_MakePoint(?, ?), 2100),?,?,?,?,?,?,?,?);";

        Connection connection = connectToDatabase();
        PreparedStatement ps = connection.prepareStatement(sql);

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
            ps.setString(13, station.last_update);

            ps.addBatch();
        }
        ps.executeBatch();
    }
}
