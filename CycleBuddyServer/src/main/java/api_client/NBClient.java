package api_client;

import java.io.IOException;

import data_models.NBCity;
import utils.HttpsRequestor;
import utils.JsonParser;
import utils.SchemeOperator;

/**
 * Retrieves the data for all bike stations for one city in the NextBikes system
 * 
 * This is currently unreliable - sometimes it's possible to read the response,
 * and sometimes reading it results in an IndexOutOfBoundsException.
 * 
 * @author Colman
 * @since 2019-02-19
 */
public class NBClient
{
    /**
     * Gets all bike stations belonging to a specific city in the NextBikes
     * system.
     * 
     * Makes the API request, stores the JSON response as a string, then parses
     * the JSON into a wrapper object and returns that to the caller.
     * 
     * @param cityID The numeric city ID of the city in question.
     * @return A WrapperNBCityOld object with the details for the chosen city.
     * @throws IllegalStateException If there is a problem parsing the JSON from the API into the wrapper.
     * @throws IOException If there is a problem with the API request.
     */
    public static NBCity getCityByID(int cityID) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.NEXT_BIKE;
        HttpsRequestor apiRequest = new HttpsRequestor(operator);
        String response = apiRequest.getStationsByCityNB(cityID);
        return JsonParser.parseResponse(response, NBCity.class);
    }
}
