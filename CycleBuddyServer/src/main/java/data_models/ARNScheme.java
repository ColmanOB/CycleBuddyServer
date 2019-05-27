package data_models;

/**
 * A wrapper class used to represent a 'scheme' operated by An Rothar Nua.
 * 
 * A scheme can correspond to one city if that city is specified, otherwise it
 * includes all cities where An Rothar Nua operates
 * 
 * @author Colman
 * @since 2019-02-24
 */
public class ARNScheme
{
    /**
     * A numeric response code to indicate the outcome of the request.
     * 
     * Possible values are as follows:
     * 
     * 0 Success 
     * 1000 Invalid Request Format 
     * 1017 Missing schemeId 
     * 1117 Invalid schemeId 
     * 1200 Failure 1201 Authentication Failure 1250 Data Unavailable
     * 1251 Rate Limit exceeded
     */
    public Integer responseCode;
    /**
     * Text response from the API, detailing the success or failure of the request.
     */
    public String responseText;
    /**
     * Date and time of the API response, in the following format: 
     * mm-dd-yyyy HH:mm:ss
     */
    public String responseDate;
    /**
     * A list of individual stations data
     */
    public Data[] data;
    /**
     * An MD5 hash calculated on the following: 
     * key + full stop + data (jsonText)
     */
    public String responseHash;
    /**
     * Represents an individual bike station.
     * 
     * @author Colman
     */
    public class Data
    {
        /**
         * Indicates which scheme the station belongs to. Possible values are: 
         * 2 = Cork 
         * 3 = Limerick 
         * 4 = Galway 
         * -1 = All schemes
         */
        public Integer schemeId;
        /**
         * The name in English of the scheme, e.g. Cork, Limerick, Galway.
         */
        public String schemeShortName;
        /**
         * A numeric identifier for the station in the An Rother Nua system.
         */
        public Integer stationId;
        /**
         * The name of the station, usually related to its location / address.
         */
        public String name;
        /**
         * The name of the bike station in Irish.
         */
        public String nameIrish;
        /**
         * Total number of bike spaces at the bike station.
         */
        public Integer docksCount;
        /**
         * The number of bikes currently available at the station.
         */
        public Integer bikesAvailable;
        /**
         * The number of free spaces to return a bike.
         */
        public Integer docksAvailable;
        /**
         * A numeric code indicating the bike station status. 
         * The An Rothar Nua documentation gives no information re: possible values.
         */
        public Integer status;
        /**
         * The latitude of the bike station.
         */
        public Double latitude;
        /**
         * The longitude of the bike station.
         */
        public Double longitude;
        /**
         * Date/time that the data was retrieved.
         */
        public String dateStatus;
    }
}
