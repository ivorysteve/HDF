package com.stephengilbane.hotel.deals;

import java.time.LocalDate;

/**
 *  Deal that represents a straight discount (i.e., fixed amount) off the total price of any stay
 *  that is three days or longer in length.
 */
public class FlatRebateThreePlusDeal 
extends Deal
{
    private int  rebateAmount;

    /**
     * Constructor.
     * @param pTxt Promotional text to display to user.
     * @param amt Amount of straight discount.
     * @param startDt Start date of discount.
     * @param endDt End date of discount.
     */
    public FlatRebateThreePlusDeal(String pTxt, int amt, LocalDate startDt, LocalDate endDt)
    {
        super(DealType.FLAT_REBATE_3PLUS, pTxt, startDt, endDt, 3);
        this.rebateAmount = amt;
    }

    /**
     * Calculate the value in monetary units that a customer would pay after
     * applying this deal for this hotel.
     * @param ratePerNight Monetary units normally charged per night at this hotel.
     * @param stayLength number of nights of the requested stay.
     * @return monetary units charged for the stay including discount.
     */
    @Override
    public int calculateFinalValue(int ratePerNight, int stayLength)
    {
        return (ratePerNight * stayLength) +  this.rebateAmount;
    }
    
    /**
     * @return  Original input value for this deal.
     */
    @Override
    public int getValue()
    {
        return this.rebateAmount;
    }
}

