package P2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import P1.PlanningEntry;
import P2.FlightSchedule.FlightEntry;
import P2.FlightSchedule.Plane;
import P3.Exception.MyException;

public class FlightEntryTest {
	//测试策略
	//对addTrain()，测试resources为空时能否加入，不为空时能否加入，能否避免重复加入
	//对removeResource()，测试能否正常删除
	//对getResource()，测试能否正常获取资源
	//对addLocation()，测试location为空时能否正常加入，不为空时能否加入，能否避免重复加入
	//对getLocation()，测试能否正常获取位置
	//对removeLocation()，测试能否正常删除位置
	//对addPlan()，测试机场未加入能否加入航班，机场已加入能否加入航班，能否避免重复加入
	//对State类，测试各种状态改变是否符合预期
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testResource() {
		FlightEntry plans = FlightEntry.getFlightEntry();
		assertEquals(plans.getResource("A"), null);
		try {
			plans.addPlane("A", "A380", 100, 10);
		}catch(MyException e){
			assert false;
		}
		try {
			plans.addPlane("B", "B380", 200, 20);
		}catch(MyException e){
			assert false;
		}
		try {
			plans.addPlane("B", "B380", 200, 20);
		}catch(MyException e){
			assert true;
		}
		
		assertEquals(plans.getResource("A").getName(), "A");
		
		try {
			plans.removeResource("A");
		}catch(MyException e){
			assert false;
		}
		try {
			plans.removeResource("A");
		}catch(MyException e){
			assert true;
		}
	}
	
	
	@Test
	public void testLocation() {
		FlightEntry plans = FlightEntry.getFlightEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		assertEquals(plans.getLocation(locationname1), null);
		
		try {
			plans.addLocation(locationname1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addLocation(locationname1);
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.addLocation(locationname2);
		}catch(MyException e) {
			assert false;
		}
		
		assertEquals(plans.getLocation(locationname1).getName(), locationname1);
		assertEquals(plans.getLocation(locationname2).getName(), locationname2);
		
		try {
			plans.removeLocation(locationname1);
		}catch(MyException e) {
			assert false;
		}
		
		assertEquals(plans.getLocation(locationname1), null);
		
		try {
			plans.removeLocation(locationname1);
		}catch(MyException e) {
			assert true;
		}
		
		
	}
	
	
	@Test
	public void testPlan() {
		FlightEntry plans = FlightEntry.getFlightEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		String begintime1 = "2020-01-01 17:00";
		String endtime1 = "2020-01-01 19:00";
		
		try {
			plans.addPlan("航班1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert true;
		}
		
		try {
			plans.addLocation(locationname1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addLocation(locationname2);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlane("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.addPlan("航班1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("航班1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert true;
		}
		
		PlanningEntry<Plane> line1 = plans.getPlan("航班1");
		
		assertEquals(line1.getState().getName(), "WAITING");
		assertEquals(line1.getLocations().get(0).getName(), locationname1);
		assertEquals(line1.getLocations().get(1).getName(), locationname2);
		assertEquals(line1.getTimeslots().get(0).getBegin().toString(), "2020-01-01 17:00");
		assertEquals(line1.getTimeslots().get(0).getEnd().toString(), "2020-01-01 19:00");
		
		assertEquals(plans.removePlan("航班1"), true);
		assertEquals(plans.getPlan("航班1"), null);
	}
	
	
	@Test
	public void testState() {
		FlightEntry plans = FlightEntry.getFlightEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		String begintime1 = "2020-01-01 17:00";
		String endtime1 = "2020-01-01 19:00";
		
		try {
			plans.addLocation(locationname1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addLocation(locationname2);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlane("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.addPlan("航班1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("航班2", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.start("航班1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.complete("航班1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.allocateResource("A", "航班1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("航班1").getName(), "ALLOCATED");
		try {
			plans.start("航班1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("航班1").getName(), "RUNNING");
		try {
			plans.cancel("航班1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.complete("航班1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getState("航班1").getName(), "ENDED");
		try {
			plans.allocateResource("A", "航班2");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("航班2").getName(), "ALLOCATED");
		try {
			plans.cancel("航班2");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getState("航班2").getName(), "CANCELLED");
		
	}
}
