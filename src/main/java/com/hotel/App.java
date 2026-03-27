package com.hotel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;


/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        LocalDate checkIn = LocalDate.now();

        LocalDate deadLine = checkIn.plusDays(10);
        long nights = ChronoUnit.DAYS.between(checkIn,deadLine);
        System.out.println("Today is date  " + checkIn);
        System.out.println("deadLine is " + deadLine);
        System.out.println("GAP " + nights);

    }
}
