## Best Hotel Deal Finder Project

**Author:** Stephen Gilbane (sgilbane@gmail.com)

## Problem Description

Imagine you’re given a data file containing hotel deals, aggregated from participating hotels. Write an app that will find the best deal given a hotel name, check-in date, and duration of stay. Print the promo_txt value for the best deal, or “no deals available” if none exists. Consider the type and value of the deals, as well as whether they apply. Your solution will be evaluated based on correctness, performance, code design, and maintainability. 

You have 24 hours to complete and submit your solution.

### Requirements:

Some aspects of the problem are intentionally vague. We're interested in your approach and decisions. Note any assumptions that you make in your solution.
You are responsible for crafting your own data set for development and testing. Be sure your solution works beyond the examples provided, below.
Gracefully handle input errors and provide informative feedback to the user
Use Java, C++, C#, or Python for your solution. You may use common libraries, but make sure to include anything necessary to compile your solution.
The types of deals are as follows:

* __rebate__: a straight discount off the total price of the stay
* __rebate_3plus__: a straight discount off the total price when the duration of stay is 3 nights or more
* __pct__: a percentage discount off the total price of the stay

The deals data file will be a comma-separated file (CSV) in the following format. You may assume the entire deal file fits in memory. 

`hotel_name,nightly_rate,promo_txt,deal_value,deal_type,start_date,end_date`

`Hotel Foobar,250,$50 off your stay 3 nights or more,-50,rebate_3plus,2016-03-01,2016-03-31`

`Hotel Foobar,250,5% off your stay,-5,pct,2016-03-01,2016-03-15`

`Hotel Foobar,250,$20 off your stay,-20,rebate,2016-03-07,2016-03-15`

 
The input to your application will be the path to the deal file, hotel name, check in date, and number of nights stay. For example:

`> BestHotelDeal ./deals.csv "Hotel Foobar" 2016-03-14 3`


### Examples:

`> BestHotelDeal ./deals.csv "Hotel Foobar" 2016-03-05 3`

`> $50 off your stay 3 nights or more`

`> BestHotelDeal ./deals.csv "Hotel Foobar" 2016-03-10 2`

`> 5% off your stay`

`> BestHotelDeal ./deals.csv "Hotel Foobar" 2016-03-20 1`

`> no deal available`


### Solution Notes

* Build instructions (Maven): `mvn clean test`
* Run instructions (Maven): `mvn spring-boot:run -Drun.arguments='./foo.csv,Hotel Foobar,2016-03-20,1'`
* **Java 8** was used, mainly for the `java.time` package.
* This used Spring Boot mainly to set the project up quickly.  There is nothing that uses Spring per se.
* Generally, I tried to be as forgiving of input as possible. If any of the lines in the configuration file were invalid, the program behaves as if there is no deal at all,  rather than rejecting the entire file.
* Money: Values are  implicitly in dollars, although some comments  indicate in **monetary units**.  A more international implementation would need to take this into account; see **JSR-354** for better money support. 
* Language: All log messages are in English here. Presumably, message strings would be replaced by, say, property keys that would reference message strings in the target language in a property file.
* Dates:  All date values are not adjusted for time zones. For this application, the assumption is that any date value applies to the time zone of the hotel.
* Logging: All logging messages that are currently directed to System.out should be changed to use a standard logging package such as **Log4j** in the production code.
* Concurrency:  There is an assumption throughout that only one thread is configuring and using a given HotelDealFinder instance. No attempt has been made to synchronize usage by multiple threads.
* There could be more testing of config input (e.g., whether start date precedes end date, whether rates and values are negative, etc.).
