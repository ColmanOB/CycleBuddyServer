package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Uses Google's Gson library to parse a JSON response from an API. 
 * 
 * The response string is passed into a wrapper object for ease of access to each element.
 * 
 * @author Colman O'B
 * @since 2019-02-19
 */

public class JsonParser
{
    /**
     * Uses Gson to parse a JSON String into a wrapper object
     * to allow easy access to all elements of the response, as follows:
     * 
     * 1. Get a Gson instance
     * 2. Use the Gson to parse a JSON String into a wrapper object
     * 3. Return the wrapper object, populated with the data from the JSON String
     * 
     * @param jsonResponse a string of JSON retrieved from thesession.org API
     * @param wrapperType the type of wrapper object into which the JSON should be parsed
     * @return a wrapper object of the type specified in the argument
     * @throws IllegalStateException if the JSON response is not valid JSON or does not match the expected structure defined in the corresponding wrapper class
     */
    public static <T> T parseResponse(String jsonResponse, Class<T> wrapperType) throws IllegalStateException
    {
        try
        {
            // Use Gson to parse the JSON into a POJO of the type passed in
            Gson gson = new GsonBuilder().create();
            T listOfResults = gson.fromJson(jsonResponse, wrapperType);
            return listOfResults;
        } 
        
        catch (JsonSyntaxException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
