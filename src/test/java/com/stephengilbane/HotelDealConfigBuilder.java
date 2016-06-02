package com.stephengilbane;

import java.time.LocalDate;

import com.stephengilbane.hotel.DealType;
import com.stephengilbane.hotel.ParserUtils;

/**
 * Class used to build test configuration input for HotelDealFinder.
 * 
 * Data input field order:
 *      hotel_name, nightly_rate, promo_txt, deal_value, deal_type, start_date, end_date
 * 
 * For single line input, call newConfiguration(), set attributes as needed, and call build().
 * For multi-line input, call newConfiguration(), set attributes for each line, call newLine() for next record,
 *    then change whatever attributes are different.  Call build() to produce final String.
 *    
 * @author stephengilbane
 *
 */
public class HotelDealConfigBuilder
{

    public static final String TEST_HOTEL_NAME = "Test Hotel";
    public static final int TEST_NIGHTLY_RATE = 25;
    public static final String  TEST_TYPE_TOKEN = DealType.FLAT_REBATE.getToken();
    public static final int TEST_NIGHTLY_VALUE = -10;
    public static final String TEST_PROMO_TEXT = "Exciting test promotional message!";
    public static final LocalDate TEST_START_DATE = LocalDate.now();
    public static final LocalDate TEST_END_DATE = TEST_START_DATE.plusDays(7);
    public static final String DELIM = ParserUtils.FIELD_DELIM;

    private StringBuilder buffer = new StringBuilder();
    private String prevLines = "";
    
    // Config values
    private String hotelName = TEST_HOTEL_NAME;
    private int nightlyRate = TEST_NIGHTLY_RATE;
    private String promoText = TEST_PROMO_TEXT;
    private int dealValue = TEST_NIGHTLY_VALUE;
    private LocalDate startDate = TEST_START_DATE;
    private LocalDate endDate = LocalDate.now();
    private String dealTypeToken = TEST_TYPE_TOKEN;

    /**
     * Constructor for default builder.
     * @return HotelDealConfigBuilder With sensible default values.
     */
    public static HotelDealConfigBuilder newConfiguration()
    {
        HotelDealConfigBuilder builder = new HotelDealConfigBuilder();
        return builder;
    }

    /******************************************
     *  Attribute setters
     *****************************************/
    public HotelDealConfigBuilder hotelName(String n)
    {
        hotelName = n;
        return this;
    }

    public HotelDealConfigBuilder hotelRate(int n)
    {
        nightlyRate = n;
        return this;
    }

    public HotelDealConfigBuilder promoText(String txt)
    {
        promoText = txt;
        return this;
    }
    
    public HotelDealConfigBuilder  dealTypeToken(String txt)
    {
        dealTypeToken = txt;
        return this;
    }    

    public HotelDealConfigBuilder  dealValue(int val)
    {
        this.dealValue = val;
        return this;
    }
    
    public HotelDealConfigBuilder startDate(LocalDate dt)
    {
        startDate = dt;
        return this;
    }
    
    public HotelDealConfigBuilder endDate(LocalDate dt)
    {
        endDate = dt;
        return this;
    }
    
    /**
     * Record the state of the builder.  Reset buffer, leave all values as is.
     * Typically, subsequent calls will only change values that change.
     */
    public HotelDealConfigBuilder newLine()
    {
        prevLines = prevLines + buildRecord() + "\n";
        buffer.setLength(0);
        return this;
    }

    /**
     * Build a single configuration line.
     * @return
     */
    public String buildRecord()
    {
        buffer.append(hotelName);
        buffer.append(DELIM);
        buffer.append(nightlyRate);
        buffer.append(DELIM);
        buffer.append(promoText);
        buffer.append(DELIM);
        buffer.append(dealValue);
        buffer.append(DELIM);
        buffer.append(dealTypeToken);
        buffer.append(DELIM);
        buffer.append(ParserUtils.formatDate(startDate));
        buffer.append(DELIM);
        buffer.append(ParserUtils.formatDate(endDate));

        return  buffer.toString();
    }
    
    /**
     * @return Contents of configuration for HotelDealFinder.
     */
    public String build()
    {
        return prevLines + buildRecord();
    }
}
