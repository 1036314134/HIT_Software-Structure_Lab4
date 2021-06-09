package debug;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;


public class FlightTest {
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testPlaneAllocation1() {
		FlightClient client = new FlightClient();
		
		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();

		Plane plane1 = new Plane();
		plane1.setPlaneAge(10);
		plane1.setPlaneNo("A");
		plane1.setPlaneType("A");
		plane1.setSeatsNum(100);
		
		Plane plane2 = new Plane();
		plane2.setPlaneAge(10);
		plane2.setPlaneNo("B");
		plane2.setPlaneType("B");
		plane2.setSeatsNum(200);
		
		Flight flight1 = new Flight();
		flight1.setFlightNo("101");
		flight1.setDepartAirport("nantong");
		flight1.setArrivalAirport("harbin");
		
		Calendar start1 = Calendar.getInstance();
		Calendar end1 = Calendar.getInstance();
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 1, 1, 15, 00, 00);
		start1.set(2020, 1, 1, 15, 00, 00);
		end1.set(2020, 1, 1, 16, 00, 00);
		flight1.setFlightDate(date1);
		flight1.setDepartTime(start1);
		flight1.setArrivalTime(end1);

		Flight flight2 = new Flight();
		flight2.setFlightNo("102");
		flight2.setFlightNo("wuhu");
		flight2.setFlightNo("harbin");

		Calendar start2 = Calendar.getInstance();
		Calendar end2 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 1, 1, 15, 00, 00);
		start2.set(2020, 1, 1, 15, 00, 00);
		end2.set(2020, 1, 1, 16, 00, 00);
		flight2.setFlightDate(date2);
		flight2.setDepartTime(start2);
		flight2.setArrivalTime(end2);
		
		Flight flight3 = new Flight();
		flight3.setFlightNo("103");
		flight3.setFlightNo("nanjing");
		flight3.setFlightNo("harbin");

		Calendar start3 = Calendar.getInstance();
		Calendar end3 = Calendar.getInstance();
		Calendar date3 = Calendar.getInstance();
		date3.set(2019, 1, 1, 15, 00, 00);
		start3.set(2019, 1, 1, 15, 00, 00);
		end3.set(2019, 1, 1, 16, 00, 00);
		flight3.setFlightDate(date3);
		flight3.setDepartTime(start3);
		flight3.setArrivalTime(end3);
		
		planes.add(plane1);
		flights.add(flight1);
		flights.add(flight2);
		assertEquals(client.planeAllocation(planes, flights), false);
	}
	
	@Test
	public void testPlaneAllocation2() {
		FlightClient client = new FlightClient();
		
		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();

		Plane plane1 = new Plane();
		plane1.setPlaneAge(10);
		plane1.setPlaneNo("A");
		plane1.setPlaneType("A");
		plane1.setSeatsNum(100);
		
		Plane plane2 = new Plane();
		plane2.setPlaneAge(10);
		plane2.setPlaneNo("B");
		plane2.setPlaneType("B");
		plane2.setSeatsNum(200);
		
		Flight flight1 = new Flight();
		flight1.setFlightNo("101");
		flight1.setDepartAirport("nantong");
		flight1.setArrivalAirport("harbin");
		
		Calendar start1 = Calendar.getInstance();
		Calendar end1 = Calendar.getInstance();
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 1, 1, 15, 00, 00);
		start1.set(2020, 1, 1, 15, 00, 00);
		end1.set(2020, 1, 1, 16, 00, 00);
		flight1.setFlightDate(date1);
		flight1.setDepartTime(start1);
		flight1.setArrivalTime(end1);

		Flight flight2 = new Flight();
		flight2.setFlightNo("102");
		flight2.setFlightNo("wuhu");
		flight2.setFlightNo("harbin");

		Calendar start2 = Calendar.getInstance();
		Calendar end2 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 1, 1, 15, 00, 00);
		start2.set(2020, 1, 1, 15, 00, 00);
		end2.set(2020, 1, 1, 16, 00, 00);
		flight2.setFlightDate(date2);
		flight2.setDepartTime(start2);
		flight2.setArrivalTime(end2);
		
		Flight flight3 = new Flight();
		flight3.setFlightNo("103");
		flight3.setFlightNo("nanjing");
		flight3.setFlightNo("harbin");

		Calendar start3 = Calendar.getInstance();
		Calendar end3 = Calendar.getInstance();
		Calendar date3 = Calendar.getInstance();
		date3.set(2019, 1, 1, 15, 00, 00);
		start3.set(2019, 1, 1, 15, 00, 00);
		end3.set(2019, 1, 1, 16, 00, 00);
		flight3.setFlightDate(date3);
		flight3.setDepartTime(start3);
		flight3.setArrivalTime(end3);
		
		planes.add(plane1);
		flights.add(flight1);
		flights.add(flight2);
	    planes.add(plane2);
		assertEquals(client.planeAllocation(planes, flights), true);
	}
	
	@Test
	public void testPlaneAllocation3() {
		FlightClient client = new FlightClient();
		
		List<Plane> planes = new ArrayList<>();
		List<Flight> flights = new ArrayList<>();

		Plane plane1 = new Plane();
		plane1.setPlaneAge(10);
		plane1.setPlaneNo("A");
		plane1.setPlaneType("A");
		plane1.setSeatsNum(100);
		
		Plane plane2 = new Plane();
		plane2.setPlaneAge(10);
		plane2.setPlaneNo("B");
		plane2.setPlaneType("B");
		plane2.setSeatsNum(200);
		
		Flight flight1 = new Flight();
		flight1.setFlightNo("101");
		flight1.setDepartAirport("nantong");
		flight1.setArrivalAirport("harbin");
		
		Calendar start1 = Calendar.getInstance();
		Calendar end1 = Calendar.getInstance();
		Calendar date1 = Calendar.getInstance();
		date1.set(2020, 1, 1, 15, 00, 00);
		start1.set(2020, 1, 1, 15, 00, 00);
		end1.set(2020, 1, 1, 16, 00, 00);
		flight1.setFlightDate(date1);
		flight1.setDepartTime(start1);
		flight1.setArrivalTime(end1);

		Flight flight2 = new Flight();
		flight2.setFlightNo("102");
		flight2.setFlightNo("wuhu");
		flight2.setFlightNo("harbin");

		Calendar start2 = Calendar.getInstance();
		Calendar end2 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date2.set(2020, 1, 1, 15, 00, 00);
		start2.set(2020, 1, 1, 15, 00, 00);
		end2.set(2020, 1, 1, 16, 00, 00);
		flight2.setFlightDate(date2);
		flight2.setDepartTime(start2);
		flight2.setArrivalTime(end2);
		
		Flight flight3 = new Flight();
		flight3.setFlightNo("103");
		flight3.setFlightNo("nanjing");
		flight3.setFlightNo("harbin");

		Calendar start3 = Calendar.getInstance();
		Calendar end3 = Calendar.getInstance();
		Calendar date3 = Calendar.getInstance();
		date3.set(2019, 1, 1, 15, 00, 00);
		start3.set(2019, 1, 1, 15, 00, 00);
		end3.set(2019, 1, 1, 16, 00, 00);
		flight3.setFlightDate(date3);
		flight3.setDepartTime(start3);
		flight3.setArrivalTime(end3);
		
		planes.add(plane1);
		flights.add(flight1);
		flights.add(flight2);
	    planes.add(plane2);
		flights.add(flight3);
		assertEquals(client.planeAllocation(planes, flights), true);
	}
}
