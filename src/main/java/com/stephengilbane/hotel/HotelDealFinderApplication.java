package com.stephengilbane.hotel;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Wrapper class to be used for Spring configuration.
 */
@SpringBootApplication
public class HotelDealFinderApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run(HotelDealFinder.class, args);
    }
}
