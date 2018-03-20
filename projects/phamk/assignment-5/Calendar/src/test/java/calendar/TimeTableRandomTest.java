package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Random Test Generator for TimeTable class.
 */

public class TimeTableRandomTest {
   
   /**
    * Generate Random Tests that tests TimeTable Class.
    */
   @Test
   public void randomTestDeleteAppt() throws Throwable{
      for(int k=0; k<1000; k++){   //1000 Tests
         //System.out.println("Test #" + k+1);
         int startHour, startMinute, startMonth, startYear, startDay, NumDaysInMonth, contains=0;
         TimeTable table = new TimeTable();
         try{
            Random random = new Random();
            LinkedList<Appt> appts = new LinkedList<Appt>();
            LinkedList<Appt> empty = null;
            Appt appt = new Appt(10, 10, 10, 2, 2018, "Potato", "Noodle");
            
            for(int j=0; j<10; j++){   //Add 10 appts to the list
               startHour = ValuesGenerator.getRandomIntBetween(random, 1, 10);      //Random valid hour
               startMinute = ValuesGenerator.getRandomIntBetween(random, 1, 59);   //Random valid minute
               startMonth = ValuesGenerator.getRandomIntBetween(random, 1, 12);   //Random valid month
               startYear = 2018;
               NumDaysInMonth= CalendarUtil.NumDaysInMonth(startYear,startMonth-1);
               startDay = ValuesGenerator.getRandomIntBetween(random, 1, NumDaysInMonth);   //Random valid day
               
               String title = "Test";
               String description = "Remove";
               Appt random_appt = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);
               
               if(startHour < 0 || startHour > 23)         //Assertions
                  assertFalse(random_appt.getValid());
               else if(startMinute < 0 || startMinute > 59)
                  assertFalse(random_appt.getValid());
               else if(startDay < 1 || startDay > NumDaysInMonth)
                  assertFalse(random_appt.getValid());
               else
                  assertTrue(random_appt.getValid());
               
               if(startHour == 10)                     //If hour = 10, place appt in
                  appts.add(appt);
               else                              //Else, place randomly generated appt in list
                  appts.add(random_appt);
            }
            assertEquals(10, appts.size());               //Size of appt list equals 10
            
            int randomize_remove = ValuesGenerator.getRandomIntBetween(random, 1, 10);   //Randomize if we want to remove null
            
            if(randomize_remove == 10){                  //If randomize = 10, try removing null
               LinkedList<Appt> new_appts = table.deleteAppt(appts, null);
               LinkedList<Appt> null_list = table.deleteAppt(empty, null);
               assertNull(new_appts);                  //Should be null
               assertNull(null_list);                  //Should be null
            }
            else if(randomize_remove == 9){               //If randomize = 9, try removing invalid appointment
               Appt invalid_appt = new Appt(-1, 10, 10, 2, 2018, "Invalid", "Nope");
               LinkedList<Appt> new_appts = table.deleteAppt(appts, invalid_appt);
               LinkedList<Appt> null_list = table.deleteAppt(empty, invalid_appt);
               assertNull(new_appts);                  //Should be null
               assertNull(null_list);                  //Should be null
            }
            else{                                 //Else try removing appt
               for(int j=0; j<10; j++){               //Check if appt is in the list, for assertion
                  Appt tempAppt = appts.get(j);
                  if(tempAppt.equals(appt)){
                     contains = 1;
                  }
               }
               LinkedList<Appt> new_appts = table.deleteAppt(appts, appt);
               LinkedList<Appt> null_list = table.deleteAppt(empty, appt);
               assertNull(null_list);                  //Should always be null
               if(contains == 0)
                  assertNull(new_appts);               //If appt is not in the list, null should be returned
               else
                  assertEquals(9, new_appts.size());      //If appt is in the list, size should decrease
            }
         }catch(NullPointerException e){
            
         }
      }
   }
   
   @Test(expected = DateOutOfRangeException.class)
   public void randomTestGetApptRange_OutOfRange() throws Throwable{   //Should eventually throw an error and stop
      TimeTable table = new TimeTable();
      int startHour, startMinute, startMonth, startYear, startDay, NumDaysInMonth, contains=0;
      for(int j=0; j<100; j++){
         try{
            Random random = new Random();
            
            startYear = 2018;
            startMonth = 2;
            NumDaysInMonth= CalendarUtil.NumDaysInMonth(startYear,startMonth-1);
            startDay = ValuesGenerator.getRandomIntBetween(random, 1, NumDaysInMonth);
            GregorianCalendar day1 = new GregorianCalendar(startYear, startDay, startMonth);   //Random day 1
            
            startDay = ValuesGenerator.getRandomIntBetween(random, 1, NumDaysInMonth);
            GregorianCalendar day2 = new GregorianCalendar(startYear, startDay, startMonth);   //Random day 2
            
            table.getApptRange(null, day1, day2);
            
         }catch(NullPointerException e){
            
         }
      }
   }
   
   @Test
   public void randomTestGetApptRange() throws Throwable{
      TimeTable table = new TimeTable();
      int startHour, startMinute, startMonth, startYear, startDay, NumDaysInMonth, contains=0;
      for(int j=0; j<100; j++){
         try{
            Random random = new Random();      //Initialize Random
            
            startYear = 2018;               //Set year
            startMonth = 2;                  //Set month
            startDay = 1;
            GregorianCalendar day1 = new GregorianCalendar(startYear, startDay, startMonth);   //Day 1
            
            NumDaysInMonth= CalendarUtil.NumDaysInMonth(startYear,startMonth-1);
            int startDay2 = ValuesGenerator.getRandomIntBetween(random, 2, NumDaysInMonth);
            GregorianCalendar day2 = new GregorianCalendar(startYear, startDay2, startMonth);   //Random day after day 1
            
            LinkedList<Appt> apptList = new LinkedList<Appt>();
            for(int k=0; k<5; k++){
               startDay = ValuesGenerator.getRandomIntBetween(random, 2, NumDaysInMonth);      //Random valid day
               startMinute = ValuesGenerator.getRandomIntBetween(random, 0, 59);            //Random valid minute
               startHour = ValuesGenerator.getRandomIntBetween(random, 0, 23);               //Random valid hour
               String title = ValuesGenerator.getString(random);                        //Random title
               String description = ValuesGenerator.getString(random);                     //Random description
               Appt appt = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);   //Random appointment
               assertTrue(appt.getValid());   //Appointment should be valid
               apptList.add(appt);            //Add appointment to list
            }
            assertEquals(5, apptList.size());   //5 appointments should have been added
            
            LinkedList<CalDay> days = table.getApptRange(apptList, day1, day2);
            
         }catch(NullPointerException e){
            
         }
      }
   }
}
