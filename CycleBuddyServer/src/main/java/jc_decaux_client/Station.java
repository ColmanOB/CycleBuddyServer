package jc_decaux_client;
/**
 * A wrapper for the JSON returned from the API for an individual bike station
 * 
 * @author Colman
 * @since 2017-11-30
 *
 */
public class Station 
	{
	public String number;
	public String name;
	public String address;
	public Coordinates position;
	public String banking;
	public String bonus; 
	public String status;
	public String contract_name;
	public String bike_stands;
	public String available_bikes;
	public String last_update;

	public class Coordinates
		{
		public String lat;
		public String lng;
		}
	}
