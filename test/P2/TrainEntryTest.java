package P2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import P1.PlanningEntry;
import P2.FlightSchedule.FlightEntry;
import P2.TrainSchedule.Train;
import P2.TrainSchedule.TrainEntry;
import P3.Exception.MyException;

public class TrainEntryTest {
	//测试策略
	//对addTrain()，测试resources为空时能否加入，不为空时能否加入，能否避免重复加入
	//对removeResource()，测试能否正常删除
	//对getResource()，测试能否正常获取资源
	//对addLocation()，测试location为空时能否正常加入，不为空时能否加入，能否避免重复加入
	//对getLocation()，测试能否正常获取位置
	//对removeLocation()，测试能否正常删除位置
	//对addPlan()，测试站点未加入能否加入路线，站点已加入能否加入路线，能否避免重复加入
	//对State类，测试各种状态改变是否符合预期
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testResource() {
		TrainEntry plans = TrainEntry.getTrainEntry();
		assertEquals(plans.getResource("A"), null);
		try {
			plans.addTrain("A", "A380", 100, 10);
			plans.addTrain("B", "B380", 200, 20);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addTrain("B", "B380", 200, 20);
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getResource("A").getName(), "A");
		try {
			plans.removeResource("A");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getResource("A"), null);
	}
	
	
	@Test
	public void testLocation() {
		FlightEntry plans = FlightEntry.getFlightEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		assertEquals(plans.getLocation(locationname1), null);
		try {
			plans.addLocation(locationname1);
			plans.addLocation(locationname2);
		}catch(MyException e) {
			assert false;
		}
		try{
			plans.addLocation(locationname1);
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getLocation(locationname1).getName(), locationname1);
		assertEquals(plans.getLocation(locationname2).getName(), locationname2);
		try {
			plans.removeLocation(locationname1);
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getLocation(locationname1), null);
	}
	
	
	@Test
	public void testPlan() {
		TrainEntry plans = TrainEntry.getTrainEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		String locationname3 = "hangzhou";
		String time1 = "2020-01-01 17:00";
		String time2 = "2020-01-01 19:00";
		String time3 = "2020-01-01 20:00";
		String time4 = "2020-01-01 22:00";
		List<String> locations = new ArrayList<String>();
		locations.add(locationname1);
		locations.add(locationname2);
		locations.add(locationname3);
		List<String> times = new ArrayList<String>();
		times.add(time1);
		times.add(time2);
		times.add(time3);
		times.add(time4);
		try {
			plans.addPlan("路线1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		
		try {
			plans.addLocation(locationname1);
			plans.addLocation(locationname2);
			plans.addLocation(locationname3);
			plans.addPlan("路线1", locations, times);
			plans.addTrain("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("路线1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		
		PlanningEntry<Train> line1 = plans.getPlan("路线1");
		
		assertEquals(line1.getState().getName(), "WAITING");
		assertEquals(line1.getLocations().get(0).getName(), locationname1);
		assertEquals(line1.getLocations().get(1).getName(), locationname2);
		assertEquals(line1.getLocations().get(2).getName(), locationname3);
		assertEquals(line1.getTimeslots().get(0).getBegin().toString(), "2020-01-01 17:00");
		assertEquals(line1.getTimeslots().get(0).getEnd().toString(), "2020-01-01 19:00");
		assertEquals(line1.getTimeslots().get(1).getBegin().toString(), "2020-01-01 20:00");
		assertEquals(line1.getTimeslots().get(1).getEnd().toString(), "2020-01-01 22:00");
		 
		assertEquals(plans.removePlan("路线1"), true);
		assertEquals(plans.getPlan("路线1"), null);
		
	}
	
	@Test
	public void testState() {
		TrainEntry plans = TrainEntry.getTrainEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		String locationname3 = "hangzhou";
		String time1 = "2020-01-01 17:00";
		String time2 = "2020-01-01 19:00";
		String time3 = "2020-01-01 20:00";
		String time4 = "2020-01-01 22:00";
		List<String> locations = new ArrayList<String>();
		locations.add(locationname1);
		locations.add(locationname2);
		locations.add(locationname3);
		List<String> times = new ArrayList<String>();
		times.add(time1);
		times.add(time2);
		times.add(time3);
		times.add(time4);
		
		
		try {
			plans.addLocation(locationname1);
			plans.addLocation(locationname2);
			plans.addLocation(locationname3);
			plans.addPlan("路线1", locations, times);
			plans.addTrain("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.addPlan("路线1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getPlan("路线1").getState().getName(), "WAITING");
		try {
			plans.start("路线1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.allocateResource("A", "路线1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getPlan("路线1").getState().getName(), "ALLOCATED");
		try {
			plans.start("路线1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getPlan("路线1").getState().getName(), "RUNNING");
		try {
			plans.complete("路线1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getPlan("路线1").getState().getName(), "ENDED");
		
	
		
	}
	
	
	
	
	
	
	

}
