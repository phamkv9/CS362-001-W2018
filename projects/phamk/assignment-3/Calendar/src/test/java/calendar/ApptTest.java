package calendar;
/**
 *  This class provides a basic set of test cases for the
 *  Appt class.
 */
import org.junit.Test;

import static org.junit.Assert.*;
public class ApptTest {
    /**
     * Test that the gets methods work as expected.
     */
	 @Test
	  public void test01()  throws Throwable  {
		 int startHour=21;
		 int startMinute=30;
		 int startDay=15;
		 int startMonth=01;
		 int startYear=2018;
		 String title="Birthday Party";
		 String description="This is my birthday party.";
		 //Construct a new Appointment object with the initial data
		 Appt appt = new Appt(startHour,
		          startMinute ,
		          startDay ,
		          startMonth ,
		          startYear ,
		          title,
		         description);

		 assertTrue(appt.getValid());
		 assertEquals(21, appt.getStartHour());
		 assertEquals(30, appt.getStartMinute());
		 assertEquals(15, appt.getStartDay());
		 assertEquals(01, appt.getStartMonth());
		 assertEquals(2018, appt.getStartYear());
		 assertEquals("Birthday Party", appt.getTitle());
		 assertEquals("This is my birthday party.", appt.getDescription());
	 }

	@Test
	public void test_isValid()  throws Throwable  {
		int startHour = -1;		//Invalid hour
		int startMinute = 30;
		int startDay = 12;
		int startMonth = 3;
		int startYear = 2018;
		String title = "bad Test";
		String description = "bad test to check";

		//making the appointment with the invalid details
		Appt appt_hour = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);

		startHour = 10;
		startMinute = 70;	//not right minutes
		//appointment with incorrect minutes
		Appt appt_minute = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);

		startMinute = 50;
		startDay = 40;		// NOt a possible day
		//appoint made with INVALID day
		Appt appt_day = new Appt(startHour, startMinute, startDay, startMonth, startYear, title, description);

		assertFalse(appt_hour.getValid());
		assertFalse(appt_minute.getValid());
		assertFalse(appt_day.getValid());
	}

	@Test
	public void test_setRecurrence() throws Throwable{
		Appt appt = new Appt(1, 1, 1, 1, 1, "Title", "Description");
		int[] recurDays = null;
		int recurBy = 1;
		int recurIncrement = 1;
		int recurNumber = 2;

		appt.setRecurrence(recurDays, recurBy, recurIncrement, recurNumber);
		assertNotNull(appt.getRecurDays());

		assertEquals(1, appt.getRecurBy());
		assertEquals(1, appt.getRecurIncrement());
		assertEquals(2, appt.getRecurNumber());

		int[] recurDays2 = {1,2};
		appt.setRecurrence(recurDays2, recurBy, recurIncrement, recurNumber);
		assertNotNull(appt.getRecurDays());
	}

	@Test
	public void test_setters() throws Throwable{
		Appt appt = new Appt(1, 1, 1, 1, 1, "default", "simple default set up");

		appt.setStartDay(3);
		appt.setStartMonth(3);
		appt.setStartYear(2019);

		appt.setStartHour(3);
		appt.setStartMinute(39);

		appt.setTitle(null);
		appt.setDescription(null);

		assertEquals(3, appt.getStartDay());
		assertEquals(3, appt.getStartMonth());
		assertEquals(2019, appt.getStartYear());

		assertEquals(3, appt.getStartHour());
		assertEquals(39, appt.getStartMinute());

		assertEquals("", appt.getTitle());
		assertEquals("", appt.getDescription());

		assertTrue(appt.getValid());
	}
}
