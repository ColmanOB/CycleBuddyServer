package utils;

/**
 * An enum used to keep track of the various bike sharing scheme operators.
 * Each operator's API works a little differently, so this is useful for conditional logic
 * where the action to be taken varies depending on which operator is involved.
 * 
 * It makes it easier to add support for new operator's APIs in future releases
 * 
 * @author Colman
 * @since 2017-12-19
 *
 */
public enum SchemeOperator
	{
	AN_ROTHAR_NUA,
	JC_DECAUX,
	NEXT_BIKE
	}
