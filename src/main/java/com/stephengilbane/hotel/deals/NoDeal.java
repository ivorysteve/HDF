package com.stephengilbane.hotel.deals;

import java.time.LocalDate;

/**
 *  Pseudo-deal that represents no discount to the input rate.
 */
public class NoDeal 
extends Deal
{
    /**
     *  Constructor
     */
    public NoDeal()
    {
        super(DealType.NO_DEAL, "No deal available", LocalDate.now(), LocalDate.now(), 1);
    }

    /**
     * Calculate the value in monetary units that a customer would pay after
     * applying this deal, which in the case of no deal at all is exactly
     * the same as the input values.
     */
    @Override
    public int calculateFinalValue(int ratePerNight, int stayLength)
    {
        return (ratePerNight * stayLength);
    }
    
    /**
     * @return  Original input value for this deal.
     */
    @Override
    public int getValue()
    {
        return 0;
    }

}
