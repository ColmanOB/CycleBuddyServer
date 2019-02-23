package api_client;

import java.io.IOException;

import data_models.ARNScheme;
import utils.HttpsRequestor;
import utils.JsonParser;
import utils.SchemeOperator;

/**
 * Gets a list of bike stations operated by An Rothar Nua.
 * 
 * Results can be limited to an individual city, or all stations can be
 * retrieved. An Rothar Nua operates in Cork, Galway and Limerick.
 * 
 * @author Colman
 */
public class ARNClient
{

    /**
     * Calls the An Rothar Nua API and parses the JSON response into a POJO.
     * Makes the API call using the city's scheme ID, captures the JSON response
     * as a string, parses the JSON into a wrapper object and returns the wrapper.
     * 
     * @param schemeID The numeric identifier for the scheme; 2 = Cork, 3 = Limerick, 4 = Galway, or -1 for all schemes.
     * @param apiKey A unique private key, must be obtained by contacting https://www.bikeshare.ie/contact-us.html.
     * @return A ARNScheme object, populated with the response from the An Rothar Nua API.
     * @throws IllegalStateException If the JSON response is not formatted correctly, or does not match the format expected by the ARNScheme class.
     * @throws IOException If there is a problem making the connection or reading the response.
     */
    public static ARNScheme getSchemeByID(int schemeID, String apiKey) throws IllegalStateException, IOException
    {
        SchemeOperator operator = SchemeOperator.AN_ROTHAR_NUA;
        HttpsRequestor apiQuery = new HttpsRequestor(operator, apiKey, schemeID);
        String response = apiQuery.getStationsBySchemeARN();
        return JsonParser.parseResponse(response, ARNScheme.class);
    }
}
