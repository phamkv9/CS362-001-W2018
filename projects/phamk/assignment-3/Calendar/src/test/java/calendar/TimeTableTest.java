package calendar;
/**
 *  This class provides a basic set of test cases for the
 *  TimeTable class.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTableTest {
   
   @Test
   public void test_constructor() throws Throwable{
      TimeTable table = new TimeTable();
      assertNotNull(table);
   }
   
   @Test
   public void test_getApptRange()  throws Throwable  {
      //Functions excludes second day, should only have one day
      TimeTable table = new TimeTable();
      GregorianCalendar day1 = new GregorianCalendar(2018, 1, 15);   //Year, month, day
      GregorianCalendar day2 = new GregorianCalendar(2018, 1, 16);
      
      //Add valid appt
      LinkedList<Appt> listAppts = new LinkedList<Appt>();
      Appt appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");   //Hour, minute, day, month, year
      listAppts.add(appt);
      
      //Add invalid appt
      Appt invalid_appt = new Appt(-14, 30, 16, 1, 2018, "Invalid", "No");
      listAppts.add(invalid_appt);
      
      //Add appt after day2
      appt = new Appt(18, 20, 24, 1, 2018, "Next Week", "Future");
      listAppts.add(appt);
      
      LinkedList<CalDay> days = new LinkedList<CalDay>();
      
      days = table.getApptRange(listAppts, day1, day2);
      
      GregorianCalendar day3 = new GregorianCalendar(2018, 1, 28);
      LinkedList<CalDay> multiple_days = new LinkedList<CalDay>();
      
      multiple_days = table.getApptRange(listAppts, day1, day3);
      
      assertEquals(1, days.size());
      assertEquals(13, multiple_days.size());
      
      //Add recurring appointment
      int[] recurDays = {1, 2};
      int recurBy = 1;
      int recurIncrement = 1;
      int recurNumber = 2;
      appt.setRecurrence(recurDays, recurBy, recurIncrement, recurNumber);
      listAppts.add(appt);
      
      multiple_days = table.getApptRange(listAppts, day1, day3);
      assertEquals(13, multiple_days.size());
   }
   
   //Asserted that it should throw an exception
   @Test(expected = DateOutOfRangeException.class)
   public void test_dateOutOfRange() throws Throwable{
      //Date should be out of range, second date is before first day
      TimeTable table = new TimeTable();
      GregorianCalendar day1 = new GregorianCalendar(2018, 1, 15);
      GregorianCalendar oor = new GregorianCalendar(2018, 1, 5);
      
      LinkedList<Appt> listAppts = new LinkedList<Appt>();
      Appt appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");   //Hour, minute, day, month, year
      listAppts.add(appt);
      
      LinkedList<CalDay> invalid_list = new LinkedList<CalDay>();
      invalid_list = table.getApptRange(listAppts, day1, oor);
   }
   
   @Test
   public void test_deleteAppt() throws Throwable{
      TimeTable table = new TimeTable();
      LinkedList<Appt> listAppts = new LinkedList<Appt>();
      LinkedList<Appt> newList = new LinkedList<Appt>();
      Appt appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");
      
      //Remove a valid appointment that's not there
      
      listAppts = table.deleteAppt(listAppts, appt);
      assertNull(listAppts);
      
      //Try to remove nothing
      
      newList = table.deleteAppt(listAppts, null);
      assertNull(newList);
      
      //Try to remove an appointment from null
      
      newList = table.deleteAppt(null, appt);
      assertNull(newList);
      
      //Invalid appointment
      listAppts = new LinkedList<Appt>();
      Appt invalid_appt = new Appt(-14, 30, 16, 1, 2018, "Invalid", "No");
      listAppts.add(invalid_appt);
      //Try to remove an invalid appointment
      
      newList = table.deleteAppt(listAppts, invalid_appt);
      assertNull(newList);
      
      assertEquals(1, listAppts.size());
      
      //Remove a valid appointment that is there, doesn't work unless there are 3 appts
      listAppts = new LinkedList<Appt>();
      appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");
      listAppts.add(appt);
      listAppts.add(appt);
      listAppts.add(appt);
      
      listAppts = table.deleteAppt(listAppts, appt);
      assertEquals(2, listAppts.size());
   }
   
   @Test
   public void test_permute() throws Throwable{
      TimeTable table = new TimeTable();
      LinkedList<Appt> apptList = new LinkedList<Appt>();
      int[] array = new int[]{};
      LinkedList<Appt> result = new LinkedList<Appt>();
      result = table.permute(apptList, array);
      assertEquals(0, result.size());
      
      Appt appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");
      Appt appt2 = new Appt(17, 20, 15, 1, 2018, "Different Title", "Another Description");
      apptList.add(appt);
      apptList.add(appt2);
      array = new int[]{1,1};
      
      result = table.permute(apptList, array);
      assertEquals(2, result.size());
   }
   
   @Test(expected = IllegalArgumentException.class)
   public void test_failed_permute() throws Throwable{
      TimeTable table = new TimeTable();
      LinkedList<Appt> apptList = new LinkedList<Appt>();
      Appt appt = new Appt(15, 30, 15, 1, 2018, "Title", "Description");
      apptList.add(appt);
      
      int[] array = new int[]{};
      LinkedList<Appt> result = new LinkedList<Appt>();
      result = table.permute(apptList, array);
   }
}

