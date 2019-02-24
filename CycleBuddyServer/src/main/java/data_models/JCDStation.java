package data_models;

import java.sql.Timestamp;

/**
 * A wrapper for the JSON returned from the JCDecaux API for a single bike station.
 * 
 * @author Colman
 * @since 2018-05-28
 *
 */
public class JCDStation {
	/**
	 * The bike station's identifier number.
	 */
	public Integer number;
	/**
	 * The bike station's name
	 */
	public String name;
	/**
	 * The street address of the bike station.
	 */
	public String address;
	/**
	 * The latitude and longitude of the bike station.
	 */
	public Coordinates position;
	/**
	 * Whether the bike station has card payment facilities.
	 */
	public Boolean banking;
	/**
	 * Bike stations in certain hilly locations reward users who return bikes there.
	 */
	public Boolean bonus;
	/**
	 * The status of the station, e.g. 'open' or 'closed'.
	 */
	public String status;
	/**
	 * The name of the contract (i.e. city) to which the station belongs.
	 */
	public String contract_name;
	/**
	 * The number of bike stands in total at the bike station.
	 */
	public Integer bike_stands;
	/**
	 * The number of currently unoccupied bike stands at the bike station.
	 */
	public Integer available_bike_stands;
	/**
	 * The number of available bikes at the bike station.
	 */
	public Integer available_bikes;
	/**
	 * The time that the data was last refreshed for the bike station.
	 */
	public double last_update;

	/**
	 * A set of geographic latitude and longitude coordinates.
	 * 
	 * @author Colman
	 */
	public class Coordinates {
		/**
		 * A latitude value.
		 */
		public Double lat;	
		/**
		 * A longitude value.
		 */
		public Double lng;
	}
}
