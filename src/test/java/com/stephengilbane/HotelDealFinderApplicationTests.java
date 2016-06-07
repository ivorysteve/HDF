package com.stephengilbane;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stephengilbane.hotel.DealResult;
import com.stephengilbane.hotel.Hotel;
import com.stephengilbane.hotel.HotelDealFinder;
import com.stephengilbane.hotel.HotelDealFinderApplication;
import com.stephengilbane.hotel.HotelDealParser;
import com.stephengilbane.hotel.deals.Deal;
import com.stephengilbane.hotel.deals.DealType;

/**
 * Unit tests for HotelDealFinder application.
 * @author stephengilbane
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HotelDealFinderApplication.class)
public class HotelDealFinderApplicationTests 
{

    @Test
    public void testSingleCorrectInputDeal()
    {
        // Set conditions.
        String inString = HotelDealConfigBuilder.newConfiguration().build();
        HotelDealParser hdParser = new HotelDealParser();

        // Run
        HotelDealFinder hdf = hdParser.parseConfigString(inString);

        // Verify
        assertThat(hdf, is(notNullValue()));

        List<Hotel> hotels = hdf.getAllHotels();
        assertThat(hotels, is(notNullValue()));
        assertThat(hotels.size(), is(1));

        Hotel hotel = hotels.get(0);
        assertThat(hotel.getName(), is(HotelDealConfigBuilder.TEST_HOTEL_NAME));
        assertThat(hotel.getNightlyRate(), is(HotelDealConfigBuilder.TEST_NIGHTLY_RATE));

        List<Deal> deals = hotel.getAllDeals();
        assertThat(deals, is(notNullValue()));
        assertThat(deals.size(), is(1));

        Deal deal = deals.get(0);
        assertThat(deal.getTypeToken(), is(HotelDealConfigBuilder.TEST_TYPE_TOKEN));
        assertThat(deal.getPromotionalText(), is(HotelDealConfigBuilder.TEST_PROMO_TEXT));
        assertThat(deal.getValue(), is(HotelDealConfigBuilder.TEST_NIGHTLY_VALUE));
        assertThat(deal.getStartDate(), is(HotelDealConfigBuilder.TEST_START_DATE));
        assertThat(deal.getEndDate(), is(HotelDealConfigBuilder.TEST_START_DATE));

        System.out.println("Original config input: " + hdf.getConfigFileText());
    }
	
    @Test
    public void testFindingDealFromSingleEntry()
    {
        // Set conditions
        String inString = HotelDealConfigBuilder.newConfiguration().build();
        HotelDealParser hdParser = new HotelDealParser();
        HotelDealFinder hdf = hdParser.parseConfigString(inString);

        // Run
        DealResult res = hdf.findBestValue(HotelDealConfigBuilder.TEST_HOTEL_NAME,
                HotelDealConfigBuilder.TEST_START_DATE, 1);

        // Verify
        assertThat(res, is(notNullValue()));
        Deal deal = res.getDeal();
        assertThat(deal.getPromotionalText(), is(HotelDealConfigBuilder.TEST_PROMO_TEXT));

        System.out.println("Deal is: " + res.toString());
    }
	
   @Test
    public void  testFindingDealFrom2LineEntry() 
    {
       // Set conditions
       String promoTextCheap = HotelDealConfigBuilder.TEST_PROMO_TEXT + " It's cheap";
       String promoTextExpensive = HotelDealConfigBuilder.TEST_PROMO_TEXT + " It's expensive!";
       int valCheap = -50;
       int valExpensive = -20;
       
       // Two deals, same hotel.
       String inString = HotelDealConfigBuilder.newConfiguration()
                .dealTypeToken(DealType.FLAT_REBATE.getToken())
                .promoText(promoTextCheap)
                .dealValue(valCheap)
                    .newLine()
                .dealTypeToken(DealType.FLAT_REBATE_3PLUS.getToken())
                .promoText(promoTextExpensive)
                .dealValue(valExpensive)
                .build();
       HotelDealParser hdParser = new HotelDealParser();
       HotelDealFinder hdf = hdParser.parseConfigString(inString);

       // Run
       DealResult res = hdf.findBestValue(HotelDealConfigBuilder.TEST_HOTEL_NAME, 
               HotelDealConfigBuilder.TEST_START_DATE, 3);
       
       // Verify
       assertThat( res, is(notNullValue()));
       Deal deal =  res.getDeal();
       assertThat(deal.getPromotionalText(), is(promoTextCheap));
    }
   
   @Test
    public void  testFindingDealFrom2DealsSameType() 
    {
       // Set conditions
       String promoTextCheap = HotelDealConfigBuilder.TEST_PROMO_TEXT + " It's cheapest";
       String promoTextNormal = HotelDealConfigBuilder.TEST_PROMO_TEXT;
       int valCheap = -50;
       int valNormal = -5;
       
       // Two deals, same hotel, same type.
       String inString = HotelDealConfigBuilder.newConfiguration()
                .dealTypeToken(DealType.FLAT_REBATE.getToken())
                .promoText(promoTextCheap)
                .dealValue(valCheap)
                    .newLine()
                .dealTypeToken(DealType.FLAT_REBATE.getToken())
                .promoText(promoTextNormal)
                .dealValue(valNormal)
                .build();
       HotelDealParser hdParser = new HotelDealParser();
       HotelDealFinder hdf = hdParser.parseConfigString(inString);

       // Run
       DealResult res = hdf.findBestValue(HotelDealConfigBuilder.TEST_HOTEL_NAME, 
               HotelDealConfigBuilder.TEST_START_DATE, 3);
       
       // Verify
       assertThat( res, is(notNullValue()));
       Deal deal =  res.getDeal();
       assertThat(deal.getPromotionalText(), is(promoTextCheap));
    }
   
   @Test
   public void  testProblemExample() 
   {
      // Set conditions
       String hotelName = "Hotel Foobar";
      
      // Three deals, same hotel.
      String inString = HotelDealConfigBuilder.newConfiguration()
            // #1
               .hotelName(hotelName)
               .hotelRate(250)
               .dealTypeToken(DealType.FLAT_REBATE_3PLUS.getToken())
               .dealValue(-50)
               .promoText("$50 off your stay 3 nights or more")
               .startDate(LocalDate.of(2016, 3, 1))
               .endDate(LocalDate.of(2016, 3, 31))
                   .newLine()
           // #2
               .dealTypeToken(DealType.REBATE_PERCENT.getToken())
               .dealValue(-5)
               .promoText("5% off your stay")
               .startDate(LocalDate.of(2016, 3, 1))
               .endDate(LocalDate.of(2016, 3, 15))
                   .newLine()
           // #3
               .dealTypeToken(DealType.FLAT_REBATE.getToken())
               .dealValue(-20)
               .promoText("$20 off your stay")
               .startDate(LocalDate.of(2016, 3, 7))
               .endDate(LocalDate.of(2016, 3, 15))
               
               .build();
      
      HotelDealParser hdParser = new HotelDealParser();
      HotelDealFinder hdf = hdParser.parseConfigString(inString);

      // Run: Hotel on date that hits all 3 date ranges and is for 3+ days
      DealResult res1 = hdf.findBestValue(hotelName, LocalDate.of(2016, 3, 15), 3);
      
      // Verify
      // #1 = (250 x 3) - 50 = $700 [WINNER]
      // #2 = (250 x 3) - (.05 x 750) = 750 - 37.50 = $712.50
      // #3 = (250 x 3) - 20 = $730
      assertThat( res1, is(notNullValue()));
      assertThat( res1.getHotel().getName(), is(hotelName));
      Deal deal1 =  res1.getDeal();
      assertThat(DealType.fromToken(deal1.getTypeToken()), is(DealType.FLAT_REBATE_3PLUS));
      
      // Pct deal
      DealResult res2 = hdf.findBestValue(hotelName, LocalDate.of(2016, 3, 10), 2);
      Deal deal2 =  res2.getDeal();
      assertThat(DealType.fromToken(deal2.getTypeToken()), is(DealType.REBATE_PERCENT));
      
      // No deal
      DealResult res3 = hdf.findBestValue(hotelName, LocalDate.of(2016, 3, 20), 1);
      Deal deal3 =  res3.getDeal();
      assertThat(DealType.fromToken(deal3.getTypeToken()), is(DealType.NO_DEAL));
   }
	
	/**
	 * ... More tests here  for negative input cases.
	 */

}
