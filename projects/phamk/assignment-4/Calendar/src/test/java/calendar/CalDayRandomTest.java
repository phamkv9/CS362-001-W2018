package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Random Test Generator for CalDay class.
 */

public class CalDayRandomTest {
   
   /**
    * Generate Random Tests that tests CalDay Class.
    */
   @Test
   public void randomTestAddAppt() throws Throwable{
      int valid=0, startHour;
      GregorianCalendar cal = new GregorianCalendar(2018, 2, 20);   //New day
      CalDay day = new CalDay(cal);
      assertEquals(0, day.getAppts().size());
      
      for(int k=0; k<1000; k++){   //1000 Tests
         //System.out.println("Test #" + k+1);
         try{
            Random random = new Random();               //Initialize random
            
            if(k<900)
               startHour = ValuesGenerator.getRandomIntBetween(random, 12, 23);      //Random hour
            else
               startHour = ValuesGenerator.getRandomIntBetween(random, 0, 11);         //Random valid hour, less than first 900 appts
            
            int startMinute = ValuesGenerator.getRandomIntBetween(random, 0, 59);      //Random valid minute
            int startMonth = ValuesGenerator.getRandomIntBetween(random, 1, 12);      //Random valid month
            int startYear = 2018;
            int NumDaysInMonth= CalendarUtil.NumDaysInMonth(startYear,startMonth-1);
            
            int startDay = ValuesGenerator.getRandomIntBetween(random, 1, NumDaysInMonth);//Random valid day
            String title = ValuesGenerator.getString(random);                     //Random title
            String description = ValuesGenerator.getString(random);                  //Random description
            Appt appt = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);   //Random appointment
            
            if(startHour < 0 || startHour > 23)                                 //Assert if appointment is valid or not
               assertFalse(appt.getValid());
            else if(startMinute < 0 || startMinute > 59)
               assertFalse(appt.getValid());
            else if(startDay < 1 || startDay > NumDaysInMonth)
               assertFalse(appt.getValid());
            else{
               assertTrue(appt.getValid());
               valid += 1;                                                //Keep track of number of valid appointments
            }
            
            day.addAppt(appt);
            assertEquals(valid, day.getSizeAppts());                           //Size of appt list equals number of valid appts
            
         }catch(NullPointerException e){
            
         }
      }
   }
}
