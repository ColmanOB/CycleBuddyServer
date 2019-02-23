package data_models;

/**
 * A wrapper for the JSON returned from the JCDecaux API for a single contract.
 * 
 * @author Colman
 * @since 2017-12-07
 *
 */
public class JCDContract {
	/**
	 * The name of the contract, typically a city / urban area.
	 */
	public String name;
	/**
	 * A list of cities in a contract, where one contract has multiple jurisdictions.
	 */
	public String[] cities;
	/**
	 * The brand name of the bike sharing scheme.
	 */
	public String commercial_name;
	/**
	 * The country where the bike scheme operates.
	 */
	public String country_code;
}
