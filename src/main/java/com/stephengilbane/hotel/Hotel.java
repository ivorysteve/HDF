package com.stephengilbane.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stephengilbane.hotel.deals.Deal;

/**
 * Class representing a hotel participating in this deal program.
 * 
 * @author stephengilbane
 *
 */
public class Hotel
{
    private final String name;
    private final int ratePerDay;
    private final List<Deal> dealList = new ArrayList<Deal>();

    /**
     * Constructor
     * 
     * @param n Name of this hotel.
     * @param rate Rate per day in monetary units.
     */
    public Hotel(String n, int rate)
    {
        this.name = n;
        this.ratePerDay = rate;
    }

    /**
     * Get name of hotel.
     * 
     * @return Name of hotel
     */
    public final String getName()
    {
        return name;
    }

    /**
     * Get nightly rate of this hotel.
     * 
     * @return  Nightly rate of hotel  in monetary units.
     */
    public int getNightlyRate()
    {
        return ratePerDay;
    }

    /**
     * Add a deal to this hotel's set of deals.
     * 
     * @param deal  Deal to add. Must not be null.
     **/
    public void addDeal(Deal deal)
    {
        this.dealList.add(deal);
    }
    
    /**
     * @return  a copy of all deals configured for this  Hotel.
     */
    public List<Deal> getAllDeals()
    {
        ArrayList<Deal>   deals = new ArrayList<Deal>();
         deals.addAll(this.dealList);
        return   deals;
    }

    /**
     * Find the best deal offered by this hotel given the input.
     * @param checkInDate Input check-in date.
     * @param stayLengthDays Input stay length in days.
     * @return The best deal offered by this hotel, or Deal.NO_DEAL if no qualifying deal was found.
     */
    public Deal findBestDeal(LocalDate checkInDate, int stayLengthDays)
    {
        int bestValue = Integer.MAX_VALUE;
        Deal bestDeal = Deal.NO_DEAL;
        for (Deal d : this.dealList)
        {
            if (!d.isStayLengthApplicable(stayLengthDays))
            {
                 continue;                
            }
            if (!d.isCheckInDateApplicable(checkInDate))
            {
                continue; 
            }
            int val = d.calculateFinalValue(this.ratePerDay, stayLengthDays);
            if (val < bestValue)
            {
                bestValue = val;
                bestDeal = d;
            }
        }

        return bestDeal;
    }
}
