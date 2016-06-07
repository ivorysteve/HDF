package com.stephengilbane.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stephengilbane.hotel.deals.Deal;

/**
 * Finds best deal for a given hotel, stay date, and stay length.
 * 
 * ASSUMPTIONS 
 * Money: Values are in dollars, although comments are in 'monetary
 *      units'. See JSR-354 for better money support. 
 * Language: All messages are in English. Presumably, message strings would be replaced by property keys that
 *      would reference message strings in the target language in a, say, property file.
 * Dates:  All date values are not adjusted for time zones. For this application, the assumption is that
 *      any date value applies to the time zone of the hotel.
 *        
 * @author stephengilbane
 *
 */
public class HotelDealFinder
{
    private String originalHotelConfigText;
    private Map<String, Hotel> hotelMap;

    /**
     * Constructor
     * 
     * @param hotelFile file containing hotel deal information.
     */
    public HotelDealFinder()
    {
        hotelMap = new HashMap<String, Hotel>();
    }
    
    /**
     * @return original configuration file text.
     */
     public String getConfigFileText()
     {
         return this.originalHotelConfigText;
     }
     /**
      * @param txt original configuration file text.  Intended for debugging and logging.
      */
      public void setConfigFileText(String txt)
      {
          this.originalHotelConfigText = txt;
      }
      
     /**
      * Add a deal to a hotel. If the hotel does not exist, created it.
      * @param hotelName Name of hotel. Must be unique.
      * @param rate  Nightly rate of this hotel.
      * @param deal  Deal to add.
      */
     public void addDeal(String hotelName, int rate, Deal deal)
     {
         Hotel h = hotelMap.get(hotelName);
         if (h == null)
         {
             h = new Hotel(hotelName, rate);
             hotelMap.put(hotelName, h);
         }
         h.addDeal(deal);
     }
     
     /**
      * @return  all hotels configured for this   Finder.
      */
     public List<Hotel> getAllHotels()
     {
         ArrayList<Hotel>  hotels = new ArrayList<Hotel>();
         hotels.addAll(this.hotelMap.values());
         return  hotels;
     }

    /**
     * Find the best value given the input values for all the hotels that have
     * registered with having a deal.
     * 
     * @param hotelName Name of the hotel to search.
     * @param checkInDate  Input check-in date.
     * @param stayLengthDays Length in days of requested stay. Must be positive
     *            integer.
     * @return DealResult Oo the best deal. Never returns null.
     */
    public DealResult findBestValue(String hotelName, LocalDate checkInDate, int stayLengthDays)
    {
        DealResult result = new DealResult(null, Deal.NO_DEAL, checkInDate, stayLengthDays);
        for (Hotel h : this.hotelMap.values())
        {
            //  Filter out hotels by name.
            if (!h.getName().equals(hotelName))
            {
                continue;
            }
            Deal d = h.findBestDeal(checkInDate, stayLengthDays);
            result = new DealResult(h, d, checkInDate, stayLengthDays);
        }
        return result;
    }

    /**
     * MAIN
     * 
     * Usage: HotelDealFinder <configFile> 
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            System.out.println("Usage: HotelDealFinder [configFile] [hotelName] [checkinDate]  [stayLengthInDays]");
            return;
        }
        // Parse commandline arguments
        String filename = ParserUtils.validateStringArg(args[0], "Filename containing hotel deals must be provided!");
        String hotelName = ParserUtils.validateStringArg(args[1], "Hotel name must be provided!");
        LocalDate checkInDate = ParserUtils.validateDateArg(args[2], "Invalid date format: should be " + ParserUtils.DATE_FORMAT);
        int stayLength = ParserUtils.validateIntegerArg(args[3], "Stay length in days must be provided.");

        HotelDealParser hdParser = new HotelDealParser();
        HotelDealFinder hdf = hdParser.createFromFile(filename);
        DealResult res = hdf.findBestValue(hotelName, checkInDate, stayLength);

        System.out.println(res);
    }


}
