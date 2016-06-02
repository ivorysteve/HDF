package com.stephengilbane.hotel;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for parsing and creating input strings.
 * @author stephengilbane
 *
 */
public class ParserUtils
{
    /**  format of all the fields in config file and on the command line. */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**  Delimiter for fields in a configuration line. */
    public static final String FIELD_DELIM = ",";
    /** Number of fields in a configuration line. */
    public static final int LINE_TOKEN_COUNT = 7;
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Validate a date input. All date input is of the form DATE_FORMAT.
     * 
     * @param s String to validate.
     * @return LocalDate parsed from input.
     * @throws IllegalArgumentException if input string is blank, null, or
     *             improperly formatted.
     */
    public static LocalDate validateDateArg(String s, String errorMessage)
    {
        if (isBlank(s))
        {
            throw new IllegalArgumentException(errorMessage);
        }
        try
        {
            LocalDate dt = DATE_FORMATTER.parse(s, LocalDate::from);
            return dt;
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Given an input date, format it in the standard form for this application.
     * @param d Date to format
     * @return  Formatted string representing this date.
     */
    public static String formatDate(LocalDate d)
    {
        if (d == null)
        {
            throw new IllegalArgumentException(" Invalid null date to format!");
        }
        String s = DATE_FORMATTER.format(d);
        return s;
    }

    /**
     * Validate a integer input as a string.
     * 
     * @param s Input string.
     * @param errorMessage Error message passed to exception.
     * @throws IllegalArgumentException if argument is null, empty, blank, or
     *             not a number.
     * @return parsed integer value.
     */
    public static int validateIntegerArg(String s, String errorMessage)
    {
        if (isBlank(s))
        {
            throw new IllegalArgumentException(errorMessage);
        }
        try
        {
            int val = Integer.parseInt(s);
            return val;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Validate a string input against being null or empty.
     * 
     * @param s Input string.
     * @param errorMessage Error message passed to exception.
     * @throws IllegalArgumentException if argument is null, empty, or blank.
     * @return input string
     */
    public static String validateStringArg(String s, String errorMessage)
    {
        if (isBlank(s))
        {
            throw new IllegalArgumentException(errorMessage);
        }
        return s;
    }

}
