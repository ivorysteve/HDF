package com.stephengilbane.hotel;

import java.time.LocalDate;

/**
 *  Deal that represents a straight discount (i.e., fixed amount) off the total price of the stay.
 */
public class PercentRebateDeal
extends Deal
{
    private int rebatePercent;

    /**
     * Constructor.
     * @param pTxt Promotional text to display to user.
     * @param amt Percentage of discount on full amount.  Must be negative. Invalid values are set to 100%.
     * @param startDt Start date of discount.
     * @param endDt End date of discount.
     */
    public PercentRebateDeal(String pTxt, int pct, LocalDate startDt, LocalDate endDt)
    {
        super(DealType.REBATE_PERCENT, pTxt, startDt, endDt, 1);
        
        // We are forgiving here and set any odd values to default to 100% (no actual deal).
        if (pct < -100 || pct > 0)
        {
            pct = 100;
        }
        this.rebatePercent = pct;
    }

    /**
     * Calculate the value in monetary units that a customer would pay after
     * applying this deal for this hotel.
     */
    @Override
    public int calculateFinalValue(int ratePerNight, int stayLength)
    {
        int originalValue = ratePerNight * stayLength;
        return (originalValue * (100 + this.rebatePercent)) / 100;
    }
    
    /**
     * @return  Original input value for this deal.
     */
    @Override
    public int getValue()
    {
        return this.rebatePercent;
    }
}
