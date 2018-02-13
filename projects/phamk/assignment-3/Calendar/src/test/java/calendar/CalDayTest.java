package calendar;
/**
 *  This class provides a basic set of test cases for the
 *  CalDay class.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;


import org.junit.Test;

import static org.junit.Assert.*;

public class CalDayTest {

	@Test
	public void test_get_and_nondefaultConst()  throws Throwable {
		GregorianCalendar cal = new GregorianCalendar(2019, 3, 10);
		CalDay d = new CalDay(cal);

		assertEquals(2019, d.getYear());
		assertEquals(3, d.getMonth());
		assertEquals(10, d.getDay());
		assertEquals(0, d.getSizeAppts());
		assertTrue(d.isValid());
	}

	@Test //setting up to test the default day
	public void test_defaultCalDay()  throws Throwable  {
		CalDay def_calDay = new CalDay();
		assertFalse(def_calDay.isValid());
	}

	@Test
	public void test_addAppt() throws Throwable{
		GregorianCalendar cal = new GregorianCalendar(2018, 3, 12);
		Appt appt = new Appt(15, 30, 12, 3, 2018, "yes", "this is the descript");	//Hour, minute, day, month, year
		CalDay d = new CalDay(cal);
		d.addAppt(appt);
		int apptadd1 = d.getSizeAppts();

		Appt appt2 = new Appt(14, 30, 12, 3, 2018, "number 2", "the next description");
		d.addAppt(appt2);
		int apptadd2 = d.getSizeAppts();

		Appt appt3 = new Appt(23, 39, 12, 3, 2018, "appt 3", "the biggest description ever");
		d.addAppt(appt3);

		//set up to check for an invalid appt to fit the range
		Appt invalid_appt = new Appt(23, 59, -1, 3, 2018, "failed appointment", "out of range and does not make sense");
		d.addAppt(invalid_appt);


		assertEquals(1, apptadd1);
		assertEquals(2, apptadd2);
		assertEquals(3, d.getSizeAppts());
	}

	@Test
	public void test_iters() throws Throwable {
		GregorianCalendar cal = new GregorianCalendar(2018, 3, 12);
		CalDay d = new CalDay(cal);
		Appt appt = new Appt(2, 30, 12, 3, 2018, "late night", "up playing games");
		d.addAppt(appt);
		assertNotNull(d.iterator());    //valid check for iterator

		d.valid = false;
		CalDay invalid = new CalDay();
		assertNull(invalid.iterator());
	}

	@Test
	public void test_toString() throws Throwable{
		GregorianCalendar cal = new GregorianCalendar(2018, 3, 12);
		CalDay d = new CalDay(cal);
		String str_day1 = d.toString();

		CalDay invalid = new CalDay();
		String str_day2 = invalid.toString();

		Appt appt = new Appt(2, 30, 12, 3, 2018, "late night", "up playing games");
		d.addAppt(appt);
		String str_day3 = d.toString();

		assertNotNull(str_day1);
		assertNotNull(str_day2);
		assertNotNull(str_day3);
	}


}
