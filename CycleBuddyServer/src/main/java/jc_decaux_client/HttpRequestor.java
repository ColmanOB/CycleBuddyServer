package jc_decaux_client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

		
/**
 * Submits a HTTPS GET request to the JCDecaux API using a URL in the format required by the API.
 * The JSON response is captured as a String and returned to the caller.
 * 
 * @author Colman O'B
 * @since 2017-11-30
 */
public class HttpRequestor
	{	
	// Define a few constants used in building the URLs for querying the API
	private static final String baseURL = "https://api.jcdecaux.com/vls/v1/stations"; 	
	private static final String contract = "Dublin";
	private static final String apiKey = "f5e326f1310fd03cf51d2bad0b9a4be7d8667879";

	/**
	 * Retrieves details of an individual bike station by its numeric ID and 'contract', i.e. city.
	 * 
	 * @param stationID the unique numeric ID of the item to be retrieved, which will generally be known from a previous search
	 * @return the JSON response from the API as a String
	 * @throws IOException if a problem was encountered setting up the connection or reading the API response
	 * @throws MalformedURLException if an invalid URL has somehow been constructed
	 */
	public String getStation(String stationID) throws IOException, MalformedURLException
		{		
		try
			{
			URL stationURL; 	// The correctly-formatted URL for the API request
			String response;	// A string of JSON data retrieved from the API
			
			// Build the URL with all necessary parameters to specify a particular bike station
			stationURL = new URL(baseURL + "/" + stationID + "?" + "contract=" + contract + "&apiKey=" + apiKey);
			
			// Call the API using a private helper method and store the response
			response = getAPIResponse(stationURL);
			
			// The JSON data returned by the API, stored as a single string
			return response;
			} 
		
		catch (MalformedURLException e) 
			{
			throw new MalformedURLException(e.getMessage());
			}
		
		catch(IOException e)
			{
			throw new IOException(e.getMessage());
			}
		}
	
	
	/**
	 * A helper method used to retrieve the JSON response from the API and return it as a string
	 * 
	 * @param connectionToURL a HttpURLConnection to API, built with a URL in the format required by the API
	 * @return a String containing the entire JSON response from the API
	 * @throws IOException if there is a problem setting up the connection or reading data from it
	 */
	private String captureResponse(HttpURLConnection connectionToURL) throws IOException
		{
		try
			{
			// Create an input stream reader to read the API response
			BufferedReader inputReader = new BufferedReader(new InputStreamReader((connectionToURL.getInputStream()),"utf-8"));
			
			// Read the response line by line, using a StringBuilder to put it all together
			String resultCurrentLine;
			StringBuilder builder = new StringBuilder();
			
			while ((resultCurrentLine = inputReader.readLine()) != null) 
				{ 
				builder.append(resultCurrentLine);	
				}
			
			// Return the completed String containing the entire response from the API to the caller
			return builder.toString();
			}
		
		catch (IOException e)
			{
			throw new IOException(e.getMessage());
			}		
		}
		
		
	/**
	 * Makes a HTTP connection to the API with the requested data in the URL, and get the response data
	 * 
	 * @param stationURL the URL used when querying the API
	 * @return the entire JSON response to the API query, returned as a single String
	 * @throws IOException if there was a problem establishing the connection to the API or reading a response
	 */
	private String getAPIResponse(URL stationURL) throws MalformedURLException, IOException
		{	
		try
			{
			String response;	// A string of JSON data returned from the API
			
			// Create the HTTPS connection
			HttpURLConnection connectionToURL =  buildConnection(stationURL);
				
			// Store the response from the API as a String
			response = captureResponse(connectionToURL);
			
			// We have our search results and can close the connection to the API.
			connectionToURL.disconnect();		
			
			// Return the API response as one long string of JSON data
			return response;
			}
	
		catch (MalformedURLException e) 
			{
			throw new MalformedURLException(e.getMessage());
			} 
	
		catch (IOException e) 
			{
			throw new IOException(e.getMessage());
			}
		}
	
	private HttpURLConnection buildConnection(URL tuneSearchURL) throws IOException
		{		
		try 
			{
			HttpURLConnection connectionToURL;
			
			// Make the HTTP(S) connection to the JCDecaux API
			connectionToURL = (HttpURLConnection) tuneSearchURL.openConnection();
			
			// Set parameters for the HTTPS connection
			connectionToURL.setRequestMethod("GET");
			connectionToURL.setRequestProperty("Accept", "application/json");
			
			//Assuming any HTTP response code other than 200 is a problem to be notified to the user
			if (connectionToURL.getResponseCode() != 200) 
				{	
				throw new IOException("HTTP error " + connectionToURL.getResponseCode());
				}
		
		// Return the HTTPS connection to the caller, in order to read the data from it
		return connectionToURL;
		} 
	
		catch (IOException e) 
			{
			throw new IOException(e.getMessage());
			}
		}	
	}
