package com.stephengilbane.hotel.deals;

import java.time.LocalDate;

/**
 *  Abstract class representing a hotel deal or discount.
 *  Concrete classes should implement the calculation of the discount in {@link calculateFinalValue()}.
 * @author stephen gilbane
 */
public abstract class Deal
{
    private final DealType dealType;
    private final String promoText;
    private final int minStayDays;
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructor (for sub-types only).
     * @param type DealType indicating type of deal.
     * @param pTxt  Promotional text to display about this deal.
     * @param startDt  Date when deal starts.
     * @param endDt  Date when deal ends.
     * @param minDays Minimum number of days required to apply this deal.
     */
    protected Deal(DealType type, String pTxt, LocalDate startDt, LocalDate endDt, int minDays)
    {
        this.dealType = type;
        this.promoText = pTxt;
        this.minStayDays = minDays;
        this.startDate = startDt;
        this.endDate = endDt;
    }

    /**
     * @return the string token used to parse the hotel deal file to indicate the type of deal.
     */
    public final String getTypeToken()
    {
        return this.dealType.getToken();
    }
    
    /**
     * @return  Promotional text to display about this deal.
     */
    public final String getPromotionalText()
    {
         return this.promoText;
    }
    
    /**
     * @return  start date for this deal.
     */
    public final LocalDate getStartDate()
    {
         return this.startDate;
    }
    
    /**
     * @return end date for this deal.
     */
    public final LocalDate getEndDate()
    {
         return this.endDate;
    }
    
    /**
     * Does a length of stay qualified for this deal?
     * @param stayLengthDays Length of stay in days.
     * @return True if stay length qualifies for this deal's minimum length.
     */
    public final boolean isStayLengthApplicable(int stayLengthDays)
    {
        return (stayLengthDays >= this.minStayDays);
    }
    
    /**
     * Does a check-in date for a stay qualify for this deal?
     * @param dt Check-in date.
     * @return True if stay check-in date qualifies for this deal.
     */
    public boolean isCheckInDateApplicable(LocalDate dt)
    {
         if (dt == null)
         {
             throw new IllegalArgumentException("Invalid null check -in date!");
         }
        if (this.startDate.equals(dt) || this.endDate.equals(dt))
        {
            return true;
        }
        return (dt.isAfter(this.startDate) && dt.isBefore(this.endDate));
    }

    /**
     * Calculate the value in monetary units that a customer would pay after
     * applying this deal.
     * 
     * @param ratePerNight Monetary units normally charged per night at this
     *            hotel.
     * @param stayLength number of nights of the requested stay
     * @return monetary units charged for the stay including discount.
     */
    abstract public int calculateFinalValue(int ratePerNight, int stayLength);
    
    /**
     * @return  Original input value for this deal;
     */
    abstract public int getValue();

    /** Static singleton representing no deal. */
    public static final Deal NO_DEAL = new NoDeal();

}
