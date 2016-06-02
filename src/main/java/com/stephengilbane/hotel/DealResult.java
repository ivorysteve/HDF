package com.stephengilbane.hotel;

import java.time.LocalDate;

/**
 * Result of search for a deal. Includes all information for display.
 */
public class DealResult
{
    private Hotel hotel;
    private final Deal deal;
    private final int stayLength;
    private final LocalDate checkInDate;
    
    /**
     * Constructor.
     * @param hotel Hotel of results.
     * @param deal Deal of result.
     * @param dt Date of check in.
     * @param stayLength Length of stay in days.
     */
    public DealResult(Hotel hotel,  Deal deal, LocalDate dt, int  stayLength)
    {
         this.hotel = hotel;
         this.deal = deal;
         this.stayLength =  stayLength;
         this.checkInDate = dt;
    }

    /**
     * @return the hotel of this result.
     */
    public Hotel getHotel()
    {
        return hotel;
    }

    /**
     * @return the Deal for this result.
     */
    public Deal getDeal()
    {
        return deal;
    }

    /**
     * @return the Length of stay in days for this result
     */
    public int getStayLength()
    {
        return stayLength;
    }
    
    /**
     * @return the original check-in date for this result
     */
    public  LocalDate getCheckinDate()
    {
        return checkInDate;
    }
    
    /**
     * @return Price for this deal for the customer.
     */
    public int getCustomerPrice()
    {
        return deal.calculateFinalValue(hotel.getNightlyRate(), stayLength);
    }

    /**
     * Display debug value of this result.
     */
    public String toFullString()
    {
        StringBuilder sb  = new StringBuilder();

        sb.append("At ");
        sb.append(this.hotel.getName());
        sb.append(" for length of stay of ");
        sb.append(this.stayLength);
        sb.append(" nights, and a check-in on ");
        sb.append(this.checkInDate);
        sb.append(", ");
        sb.append(this.deal.getPromotionalText());
        sb.append(" Your final price would be: $");
        sb.append(this.getCustomerPrice());

        return sb.toString();
    }
    
    /**
     * Override toString.
     */
    @Override
    public String toString()
    {
        StringBuilder sb  = new StringBuilder();
        sb.append(this.deal.getPromotionalText());
        return sb.toString();
    }
}
