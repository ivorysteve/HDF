package com.stephengilbane.hotel;

/**
 *  Supported types of  hotel deals.
 */
public enum DealType
{
    NO_DEAL("none"),
    FLAT_REBATE("rebate"),
    FLAT_REBATE_3PLUS("rebate_3plus"),
    REBATE_PERCENT("pct");
    
    private final String token;
    
    /**
     * Constructor
     * @param t  Token in configuration file indicating type.
     */
    private DealType(String t)
    {
        token = t;
    }

    /** @return Token in configuration file indicating this type. */
    public String getToken() { return token; }
    
    /**
     * Convert a string token to a  DealType.
     * @param s String to convert.
     * @return  Corresponding DealType or null if not found.
     */
    public static DealType fromToken(String s)
    {
        for (DealType t : DealType.values())
        {
            if (t.token.equals(s))
            {
                return t;
            }
        }
        return null;
    }
}
