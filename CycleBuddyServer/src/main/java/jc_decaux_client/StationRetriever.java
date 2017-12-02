package jc_decaux_client;
import java.io.IOException;
import java.net.MalformedURLException;


/**
 * Retrieves the data for a single bike station
 * 
 * @author Colman
 * @since 2017-11-30
 */
public class StationRetriever 
	{
	
	/**
	 * Gets the details of an individual bike station based on its numeric ID
	 * 
	 * @param stationID the numeric ID of the bike station, possibly known from a previous search
	 * @return a Station object with the details of the chosen bike station
	 * @throws IllegalStateException if there is a problem parsing the JSON from the API into the expected structure
	 * @throws IOException if there is a problem with the HTTPS request to the API
	 */
	public Station getStationByID(String stationID) throws IllegalStateException, IOException
		{
		try
			{
			// Make the API call using the the station ID and store the JSON that is returned as a String
			HttpRequestor searcher = new HttpRequestor();
			String response = searcher.getStation(stationID);
			
			// Parse the returned JSON into a wrapper class to allow access to all elements
			JsonResponseParser jsonParser = new JsonResponseParser(response);
			Station parsedResults = jsonParser.parseResponse(Station.class);
			
			return parsedResults;
			}
		
		catch (MalformedURLException e)
			{
			throw new IOException(e.getMessage());
			}
		
		catch (IOException e)
			{
			throw new IOException(e.getMessage());
			}
		
		catch (IllegalStateException e)
			{
			throw new IllegalStateException(e.getMessage());
			}			
		}
	}

