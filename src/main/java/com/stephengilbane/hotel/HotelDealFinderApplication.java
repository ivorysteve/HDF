package com.stephengilbane.hotel;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelDealFinderApplication {

	public static void main(String[] args) 
	{
	    System.out.println("WE GOT: " + args);
		SpringApplication.run(HotelDealFinder.class, args);
	}
}
