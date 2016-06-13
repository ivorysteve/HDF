package com.stephengilbane.hotel;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.StringTokenizer;

import com.stephengilbane.hotel.deals.Deal;
import com.stephengilbane.hotel.deals.DealType;
import com.stephengilbane.hotel.deals.FlatRebateDeal;
import com.stephengilbane.hotel.deals.FlatRebateThreePlusDeal;
import com.stephengilbane.hotel.deals.PercentRebateDeal;

/**
 * From the problem statement:
 * 
 * The deals data file will be a comma-separated file (CSV) in the following format:
 * 
 *      hotel_name, nightly_rate, promo_txt, deal_value, deal_type, start_date, end_date
 * 
 * You may assume the entire deal file fits in memory.
 *
 */
public class HotelDealParser
{
    /**
     * Constructor
     */
    public HotelDealParser()
    {
        // Nothing to do.
    }

    /**
     * Factory method for creating a new HotelDealFinder from a configuration
     * file.
     * 
     * @param filename Name of configuration file.
     * @return HotelDealFinder created from file.
     * @throws IllegalArgumentException if the file name is missing, or the file
     *             cannot be read.
     */
    public HotelDealFinder createFromFile(String configFilename)
    {
        if (isBlank(configFilename))
        {
            throw new IllegalArgumentException();
        }
        File f = new File(configFilename);
        if (!f.exists())
        {
            throw new IllegalArgumentException(String.format("File %s must exist and be readable!", configFilename));
        }

        try
        {
            HotelDealFinder hdf = parseConfigFile(f);
            return hdf;
        }
        catch (IOException ex)
        {
            System.out.println("Invalid file " + configFilename + ": " + ex.getMessage());
            return null;
        }
    }

    /**
     * Create a new HotelDealFinder from a configuration file.
     * @param f File to parse.
     * @return configured HotelDealFinder object.
     * @throws IOException on any input I/O problem.
     */
    public HotelDealFinder parseConfigFile(File f) throws IOException
    {
        Reader fstream = new InputStreamReader(new FileInputStream(f), "UTF-8");
        BufferedReader in = new BufferedReader(fstream);
        return parseConfigInfo(in);
    }

    /**
     * Create a new HotelDealFinder from a configuration string. Intended mainly for testing purposes.
     * @param s String to parse.
     * @return configured HotelDealFinder object.
     */
    public HotelDealFinder parseConfigString(String s)
    {
        Reader fstream = new StringReader(s);
        BufferedReader in = new BufferedReader(fstream);
        return parseConfigInfo(in);
    }

    /***
     * Parse an entire Hotel Deal configuration.
     * @param in Data to parse.
     * @return configured HotelDealFinder object.
     */
    public HotelDealFinder parseConfigInfo(BufferedReader in)
    {
        HotelDealFinder hdf = new HotelDealFinder();
        StringBuilder sb = new StringBuilder();
        String s = null;
        try
        {
            while ((s = in.readLine()) != null)
            {
                sb.append(s);
                sb.append('\n');
                parseLine(hdf, s);
            }
        }
        catch (IOException ioEx)
        {
            // Ignore I/O problems, but log.
            System.out.println("Warning: " + ioEx.getMessage());
            // bail out to ...
        }

        // Record original input, for debugging.
        hdf.setConfigFileText(sb.toString());
        
        return hdf;
    }

    /**
     * Parse a single line of the hotel deal configuration file. Add any new
     * Deal that has been defined to our HotelDealFinder.
     * 
     * @param hdf HotelDealFinder to configure.
     * @param s String to parse.
     */
    private static void parseLine(HotelDealFinder hdf, String s)
    {
        StringTokenizer tok = new StringTokenizer(s, ParserUtils.FIELD_DELIM);
        int tokenCount = tok.countTokens();
        if (tokenCount != ParserUtils.LINE_TOKEN_COUNT)
        {
            System.out.println("Warning: Improperly formatted deal line;  ignoring: " + s);
            return;
        }
        try
        {
            String name = getNextInput(tok, "hotel name");
            String rate = getNextInput(tok, "hotel nightly rate");
            String promoTxt = getNextInput(tok, "promotional text");
            String value = getNextInput(tok, "deal value");
            String type = getNextInput(tok, "deal type");
            String start = getNextInput(tok, "deal start date");
            String end = getNextInput(tok, "deal and date");

            LocalDate startDate = ParserUtils.validateDateArg(start, "start date");
            LocalDate endDate = ParserUtils.validateDateArg(end, "end date");
            int rateVal = ParserUtils.validateIntegerArg(rate, "hotel rate");
            int valueVal = ParserUtils.validateIntegerArg(value, "discout value");

            Deal deal = getDealFromType(type, promoTxt, valueVal, startDate, endDate);
            hdf.addDeal(name, rateVal, deal);
        }
        catch (Exception ex)
        {
            System.out.println("Warning: " + ex.getMessage());
            return;
        }
    }

    /**
     * Get a token of input and check for it being a blank.
     * @param tok
     * @param fName  field name for error message
     * @return next field value,  trimmed.
     * @throws IllegalArgumentException if field is blank.
     */
    private static String getNextInput(StringTokenizer tok, String fName)
    {
        String s = tok.nextToken().trim();
        if (isBlank(s))
        {
            throw new IllegalArgumentException("Missing field: " + fName);
        }
        return s;
    }

    /**
     * Central factory method for Deals.
     * 
     * @param typeTok Token indicating DealType.
     * @param promoTxt Promotional text displayed to user.
     * @param val Deal value applied with each specific algorithm.
     * @param startDate Start of deal.
     * @param endDate End of deal.
     * @return Newly created Deal object.
     */
    private static Deal getDealFromType(String typeTok, String promoTxt, int val, LocalDate startDate,
            LocalDate endDate)
    {
        DealType dt = DealType.fromToken(typeTok);
        if (dt == null)
        {
            System.out.println("Warning:  Unsupported deal type " + typeTok);
            return Deal.NO_DEAL;
        }
        switch (dt) {
        case FLAT_REBATE:
            return new FlatRebateDeal(promoTxt, val, startDate, endDate);
        case FLAT_REBATE_3PLUS:
            return new FlatRebateThreePlusDeal(promoTxt, val, startDate, endDate);
        case REBATE_PERCENT:
            return new PercentRebateDeal(promoTxt, val, startDate, endDate);
        default:
            // We would reach here if we had defined a new DealType but there was no
            // corresponding Deal class.
            System.out.println("Warning:  Unsupported deal type " + typeTok);
            return Deal.NO_DEAL;
        }
    }

}
