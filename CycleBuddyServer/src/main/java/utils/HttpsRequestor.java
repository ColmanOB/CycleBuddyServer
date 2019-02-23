package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

/**
 * Sends a HTTPS request to a chosen API, using the format specified by the API
 * publisher.
 * 
 * The response, always in JSON format at the time writing, is captured as a
 * String and returned to the caller.
 * 
 * @author Colman O'B
 * @since 2018-05-25
 */
public class HttpsRequestor
{
    // Base URLs for the various bike share provider APIs
    private static final String jcDecauxBaseURL = "https://api.jcdecaux.com/vls/v1/";
    private static final String nextBikesBaseURL = "https://api.nextbike.net/maps/nextbike-live.json";
    private static final String anRotharNuaBaseURL = "https://data.bikeshare.ie/dataapi/resources/station/data/list";

    // JCDecaux and An Rothar Nua require an API key,
    private String apiKey;
    // Used for An Rothar Nua to determine which city's data should be queried.
    private int schemeID;

    // Will be one of the operators specified in the SchemeOperator enum.
    private SchemeOperator operator;

    /**
     * Constructor used where the only piece of information required is an API key.
     * 
     * @param operator One of the possible schemes listed in the SchemeOperator enum.
     * @param apiKey An API key obtained from the API publisher
     */
    public HttpsRequestor(SchemeOperator operator, String apiKey)
    {
        this.apiKey = apiKey;
    }

    /**
     * Constructor used where a scheme ID that is not part of the URL must be passed in.
     * 
     * This is currently only used for An Rothar Nua.
     * 
     * @param operator One of the possible schemes listed in the SchemeOperator enum.
     * @param apiKey An API key obtained from the publisher, where one is required.
     * @param schemeID At the time of writing, this was specific to An Rothar Nua.
     */
    public HttpsRequestor(SchemeOperator operator, String apiKey, int schemeID)
    {
        this.apiKey = apiKey;
        this.schemeID = schemeID;
        this.operator = operator;
    }

    /**
     * Constructor where no API key or scheme ID etc. needs to be passed in.
     * 
     * @param operator
     *            One of the possible schemes listed in the SchemeOperator enum.
     */
    public HttpsRequestor(SchemeOperator operator)
    {
        this.operator = operator;
    }

    /**
     * Retrieves details of an individual bike station from the JCDecaux API,
     * using its numeric ID and 'contract', i.e. city. Builds the URL, queries
     * the API and returns the JSON response.
     * 
     * @param contract The name of the 'contract', typically the name of a city.
     * @param stationID The unique numeric ID of the item to be retrieved
     * @return The JSON response from the API as a String.
     * @throws IOException If there was a problem setting up the connection or reading the response.
     */
    public String getStationJCD(String contract, int stationID) throws IOException
    {
        URL stationURL = new URL(jcDecauxBaseURL + "stations/" + stationID + "?" + "contract=" + contract + "&apiKey=" + apiKey);
        return getAPIResponse(stationURL, "GET");
    }

    /**
     * Retrieves a list of stations belonging to a particular "contract".
     * "Contract" is synonymous with "city" or "town" in the JCDecaux API.
     * Builds the URL, queries the API and returns the JSON response.
     * 
     * @param contract The name of the contract, i.e. typically the name of the city.
     * @return the JSON response from the API as a String.
     * @throws IOException If there was a problem setting up the connection or reading the response
     */
    public String getStationsByContractJCD(String contract) throws IOException
    {
        URL contractURL = new URL(jcDecauxBaseURL + "stations?contract=" + contract + "&apiKey=" + apiKey);
        return getAPIResponse(contractURL, "GET");
    }

    /**
     * Retrieves a list of all contracts, i.e. JCDecaux bike sharing schemes.
     * Typically each town or city has its own "contract". Builds the URL,
     * queries the API using a helper method and returns the JSON response.
     * 
     * @return the JSON response from the API as a String.
     * @throws IOException If there was a problem setting up the connection or reading the response.
     */
    public String getContractListJCD() throws IOException
    {
        URL contractURL = new URL(jcDecauxBaseURL + "contracts?" + "&apiKey=" + apiKey);
        return getAPIResponse(contractURL, "GET");
    }

    /**
     * Retrieves a list of all bike stations operated by JCDecaux. This gets all
     * stations regardless of city or country. Builds the URL, queries the API
     * with a helper method and returns the JSON response.
     * 
     * @return the JSON response from the API as a String
     * @throws IOException If there was a problem making the connection or reading the response
     */
    public String getAllStationsJCD() throws IOException
    {
        URL contractURL = new URL(jcDecauxBaseURL + "stations?&apiKey=" + apiKey);
        return getAPIResponse(contractURL, "GET");
    }

    /**
     * Queries the NextBikes API, specifying one individual city by its ID.
     * Builds the URL, queries the API with a helper method, and returns the
     * JSON response.
     * 
     * @param cityID The numeric ID of the city within the NextBikes system, e.g. Belfast is 238.
     * @return The JSON response from the NextBikes API as a String.
     * @throws IOException If there was a problem setting up the connection or reading the response.
     */
    public String getStationsByCityNB(int cityID) throws IOException
    {
        URL contractURL = new URL(nextBikesBaseURL + "?city=" + cityID);
        return getAPIResponse(contractURL, "GET");
    }

    /**
     * Queries the NextBikes API, without specifiying a particular city.
     * 
     * @return The JSON response from the NextBikes API as a String.
     * @throws IOException If there was a problem making the connection or reading the response.
     */
    public String getStationsByCityNB() throws IOException
    {
        URL contractURL = new URL(nextBikesBaseURL);
        return getAPIResponse(contractURL, "GET");
    }

    /**
     * Queries the An Rothar Nua API, using the city ID passed in the
     * constructor. Calls the API using the getAPIResponse helper method.
     * 
     * @return The JSON response from the An Rothar Nua API.
     * @throws IOException If there was a problem making the connection or reading the response.
     */
    public String getStationsBySchemeARN() throws IOException
    {
        URL apiURL = new URL(anRotharNuaBaseURL);
        return getAPIResponse(apiURL, "POST");
    }

    /**
     * Reads the JSON response to an API request and returns it as a string.
     * 
     * @param connectionToAPI A HttpsURLConnection to an API (retrieved using the buildConnection() method).
     * @return A String containing the JSON response from the API.
     * @throws IOException If there is a problem setting up the connection or reading data from it.
     */
    private String captureResponse(HttpsURLConnection connectionToAPI) throws IOException
    {
        // Create an input stream reader to read the API response
        BufferedReader inputReader = new BufferedReader(new InputStreamReader((connectionToAPI.getInputStream()), "utf-8"));
        String json = inputReader.lines().collect(Collectors.joining());
        return json;
    }

    /**
     * Makes a HTTPS connection to an API using its URL, captures the response
     * data and returns it.
     * 
     * @param stationURL The URL used when querying the API.
     * @return The entire JSON response to the API query, returned as a single String.
     * @throws IOException If there was a problem making the connection or reading the response.
     */
    private String getAPIResponse(URL apiURL, String httpMethod) throws IOException
    {
        HttpsURLConnection connectionToAPI = buildConnection(apiURL, httpMethod);
        String response = captureResponse(connectionToAPI);
        connectionToAPI.disconnect();
        return response;
    }

    /**
     * Establishes a HTTPS connection using a URL, and returns the HTTPS
     * connection, so the caller can read data from it.
     * 
     * Different actions are taken depending on scheme operator
     * 
     * @param ApiURL The URL used to query the API.
     * @return A HttpsURLConnection connected to the chosen API.
     * @throws IOException In the event of a non-200 HTTP response, or if the attempt to create the connection fails.
     */
    private HttpsURLConnection buildConnection(URL ApiURL, String httpMethod) throws IOException
    {
        // Make the HTTPS connection to the API
        HttpsURLConnection connectionToURL = (HttpsURLConnection) ApiURL.openConnection();
        // Set the HTTPS method (normally either GET or POST)
        connectionToURL.setRequestMethod(httpMethod);

        // Do the required setup depending on the scheme operator
        if (operator == SchemeOperator.JC_DECAUX)
        {
            connectionToURL.setRequestProperty("Accept", "application/json");
        }

        if (operator == SchemeOperator.NEXT_BIKE)
        {
            connectionToURL.setRequestProperty("Accept", "application/json");
        }

        if (operator == SchemeOperator.AN_ROTHAR_NUA)
        {
            connectionToURL.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Assemble the parameter string to be sent in the POST request
            String requestParameters = "key=" + apiKey + "&schemeId=" + schemeID;

            // Send the POST request
            connectionToURL.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(connectionToURL.getOutputStream());
            writer.writeBytes(requestParameters);
            writer.flush();
            writer.close();
        }

        // Assuming any non-200 HTTP response code should be notified to the user
        if (connectionToURL.getResponseCode() != 200)
        {
            throw new IOException("HTTP error " + connectionToURL.getResponseCode());
        }

        // Return the HTTPS connection to the caller, in order to read the data from it
        return connectionToURL;
    }
}
