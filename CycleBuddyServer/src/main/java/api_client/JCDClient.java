package api_client;

import java.io.IOException;

import data_models.JCDContract;
import data_models.JCDStation;
import utils.HttpsRequestor;
import utils.JsonParser;
import utils.SchemeOperator;

/**
 * Used for retrieving details related to the JC Decaux bike sharing scheme. 
 * In Ireland JC Decaux operates the Dublin Bikes scheme.
 * 
 * This class contains static methods to do the following:
 * 
 * - Retrieve one specific bike station by its ID 
 * - Retrieve all bike stations in a 'contract', i.e. city 
 * - Retrieve all JCDecaux bike stations, regardless of location 
 * - Retrieve a list of 'contracts', i.e. cities where JC Decaux operates
 * 
 * @author Colman
 *
 */
public class JCDClient
{

    /**
     * Gets the details of an individual bike station, based on its "contract",
     * i.e. city, and its numeric ID.
     * 
     * Makes the API query, captures the JSON response as a string, parses the
     * JSON response into a wrapper object and returns the wrapper.
     * 
     * @param contract An ID for an individual town/city in the JC Decaux system.
     * @param stationID The numeric ID of the bike station
     * @param apiKey An API key provided by JC Decaux to a specific developer.
     * @return A JCDStation object with the details of the chosen bike station.
     * @throws IllegalStateException If there is a problem parsing the JSON from the API into the wrapper.
     * @throws IOException If there is a problem with the HTTPS request to the API.
     */
    public static JCDStation getStationByID(String contract, int stationID, String apiKey) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.JC_DECAUX;
        HttpsRequestor apiQuery = new HttpsRequestor(operator, apiKey);
        String response = apiQuery.getStationJCD(contract, stationID);
        return JsonParser.parseResponse(response, JCDStation.class);
    }

    /**
     * Gets a list of every bike station belonging to a single "contract", i.e.
     * typically a city.
     * 
     * Makes the API query, stores the JSON response as a String, parses the
     * JSON into a wrapper object and returns that wrapper.
     * 
     * @param contract The name of the contract, typically the city.
     * @param apiKey An API key provided by JC Decaux to a particular developer.
     * @return an array of JCDStation objects.
     * @throws IllegalStateException If there is a problem parsing the JSON into the wrapper.
     * @throws IOException If there is a problem with the HTTPS request to the API.
     */
    public static JCDStation[] getStationListByContract(String contract, String apiKey) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.JC_DECAUX;
        HttpsRequestor apiQuery = new HttpsRequestor(operator, apiKey);
        String response = apiQuery.getStationsByContractJCD(contract);
        return JsonParser.parseResponse(response, JCDStation[].class);
    }

    /**
     * Gets a list of every bike station operated by JCDecaux, regardless of
     * city or country.
     * 
     * Makes the API query, stores the JSON response as a string, and parses the
     * JSON into a wrapper object that is returned.
     * 
     * @param apiKey An API key provided by JC Decaux to an individual developer.
     * @return An array of JCDStation objects.
     * @throws IllegalStateException If the JSON response does not match the structure of the wrapper.
     * @throws IOException If there is a problem with the HTTPS request to the API.
     */
    public static JCDStation[] getStationListAll(String apiKey) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.JC_DECAUX;
        HttpsRequestor apiQuery = new HttpsRequestor(operator, apiKey);
        String response = apiQuery.getAllStationsJCD();
        return JsonParser.parseResponse(response, JCDStation[].class);
    }

    /**
     * Gets a list of every JC Decaux "contract" (synonymous with town or city).
     * 
     * Makes the API request, stores the JSON response as a string, then parses
     * the JSON into a wrapper object which is returned to the caller.
     * 
     * @param apiKey An API key provided by JC Decaux to a particular developer.
     * @return An array of JCDContract objects.
     * @throws IllegalStateException If there is a problem parsing the JSON into the wrapper.
     * @throws IOException If there is a problem with the HTTPS request to the API.
     */
    public static JCDContract[] getContractList(String apiKey) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.JC_DECAUX;
        HttpsRequestor apiQuery = new HttpsRequestor(operator, apiKey);
        String response = apiQuery.getContractListJCD();
        return JsonParser.parseResponse(response, JCDContract[].class);
    }
}
