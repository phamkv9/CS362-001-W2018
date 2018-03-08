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
		//test to check the dates
		TimeTable table = new TimeTable();
		GregorianCalendar day1 = new GregorianCalendar(2018, 1, 14);
		GregorianCalendar day2 = new GregorianCalendar(2018, 1, 19);

		//set up valid appts
		LinkedList<Appt> listAppts = new LinkedList<Appt>();
		Appt appt = new Appt(10, 10, 14, 1, 2018, "Ya", "Ya that");	//Hour, minute, day, month, year
		listAppts.add(appt);

		//this is to check for an appt that shouldn't work.
		Appt invalid_appt = new Appt(-10, 30, 19, 1, 2018, "Invalid", "No");
		listAppts.add(invalid_appt);

		//Appt add AFTER the second date to see if it catches it
		appt = new Appt(13, 30, 27, 1, 2018, "Late appointment", "You're late");
		listAppts.add(appt);

		LinkedList<CalDay> days = new LinkedList<CalDay>();
		days = table.getApptRange(listAppts, day1, day2);

		GregorianCalendar day3 = new GregorianCalendar(2018, 1, 28);
		LinkedList<CalDay> multiple_days = new LinkedList<CalDay>();
		multiple_days = table.getApptRange(listAppts, day1, day3);

		assertEquals(5, days.size());
		assertEquals(14, multiple_days.size());

		//set up for reoccurances.
		int[] recurDays = {1, 2};
		int recurIncrement = 1;
		int recurNumber = 2;
		int recurBy = 1;
		appt.setRecurrence(recurDays, recurBy, recurIncrement, recurNumber);
		listAppts.add(appt);
		multiple_days = table.getApptRange(listAppts, day1, day3);
		assertEquals(13, multiple_days.size());
	}

	@Test
	public void test_deleteAppt() throws Throwable{
		TimeTable table = new TimeTable();
		LinkedList<Appt> listAppts = new LinkedList<Appt>();
		LinkedList<Appt> newList = new LinkedList<Appt>();
		Appt appt = new Appt(12, 30, 13, 1, 2018, "Title", "Description");

		//removal of appt SHOULDN'T work unless theres more than 2 in the linked list
		listAppts = new LinkedList<Appt>();
		appt = new Appt(12, 30, 13, 1, 2018, "Title", "Description");
		listAppts.add(appt);
		listAppts.add(appt);
		listAppts.add(appt);
		listAppts = table.deleteAppt(listAppts, appt);
		assertEquals(2, listAppts.size());

		//an appointmnet that doesn't work / is not Valid
		listAppts = new LinkedList<Appt>();
		Appt invalid_appt = new Appt(-10, 30, 21, 1, 2018, "out of range dates", " too late");
		listAppts.add(invalid_appt);
		//Trying to remove the invalid appt
		newList = table.deleteAppt(listAppts, invalid_appt);
		assertNull(newList);
		assertEquals(1, listAppts.size());

		//Try to remove an appointment from null
		newList = table.deleteAppt(null, appt);
		assertNull(newList);

		//Remove a valid appointment that's not there
		listAppts = table.deleteAppt(listAppts, appt);
		assertNull(listAppts);

		//Try to remove nothing
		newList = table.deleteAppt(listAppts, null);
		assertNull(newList);

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

	//hoping to assert and look for an exception
	@Test(expected = DateOutOfRangeException.class)
	public void test_dateOutOfRange() throws Throwable{
		//catching out of range.
		TimeTable table = new TimeTable();
		GregorianCalendar day1 = new GregorianCalendar(2019, 3, 17);
		GregorianCalendar oor = new GregorianCalendar(2019, 3, 7);

		LinkedList<Appt> listAppts = new LinkedList<Appt>();
		Appt appt = new Appt(23, 30, 17, 3, 2019, "Late", "Late night games");
		listAppts.add(appt);

		LinkedList<CalDay> invalid_list = new LinkedList<CalDay>();
		invalid_list = table.getApptRange(listAppts, day1, oor);
	}

}
