package P1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlanningEntryTest {
	//测试策略
	//对PlanningEntry类
	//对状态改变函数，测试默认状态下能不能改变
	//对getState(), 测试默认状态是否为"WAITING"
	//对addResource(), 测试resources为空时能否加入，不为空时能否加入，是否能避免重复加入。
	//对removeResource(), 测试能否正确删除，资源不存在时是否会误删
	//对addLocation(), 测试locations为空时能否加入，不为空时能否加入，是否能避免重复加入。
	//对removeLocation(), 测试能否正确删除，位置不存在时是否会误删
	
	//对Timeslot类
	//对islegal()，测试能否判断出输入是否合法
	//对isConflict()，测试时间段不冲突和冲突两种情况
	//对addTimeslot()，测试timeslots为空时能否加入，不为空时能否加入，是否避免重复加入。
	//对getBegintime()和getBegintime()，测试能否正确提取开始与结束时间
	
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testgetName() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.getName(), name);
	}
	
	@Test
	public void teststart() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.start(), false);
	}
	
	@Test
	public void testcancel() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.cancel(), true);
	}
	
	@Test
	public void testblock() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.block(), false);
	}
	
	@Test
	public void testcomplete() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.complete(), false);
	}
	
	@Test
	public void testgetState() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.getState().getName(), "WAITING");
	}
	
	@Test
	public void testResource() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		assertEquals(plan.getResources().isEmpty(), true);
		assertEquals(plan.addResource(new Resource("feiji1")), true);
		assertEquals(plan.getResources().size(), 1);
		assertEquals(plan.addResource(new Resource("feiji2")), true);
		assertEquals(plan.getResources().size(), 2);
		assertEquals(plan.addResource(new Resource("feiji2")), false);
		assertEquals(plan.getResources().size(), 2);
		assertEquals(plan.removeResource("feiji2"), true);
		assertEquals(plan.getResources().size(), 1);
		assertEquals(plan.removeResource("feiji2"), false);
	}
	
	@Test
	public void testLocation() {
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		Location location1 = Location.getNewLocation("wuhu");
		Location location2 = Location.getNewLocation("nantong");
		
		assertEquals(plan.addLocation(location1), true);
		assertEquals(plan.addLocation(location2), true);
		assertEquals(plan.addLocation(location2), false);
		assertEquals(plan.getLocations().get(0).getName().equals(location1.getName()), true);
		assertEquals(plan.getLocation("wuhu").getName().equals("wuhu"), true);
		assertEquals(plan.removeLocation(location1.getName()), true);
		assertEquals(plan.removeLocation(location1.getName()), false);
		

	}
	
	@Test
	public void testTime(){
		String name = "wuhuqifei";
		PlanningEntry<Resource> plan = PlanningEntry.getNewPlanningEntry(name);
		
		Time time1 = Time.getNewTime(2020, 3, 21, 10, 30);
		Time time2 = Time.getNewTime(2020, 3, 21, 11, 00);
		Time time3 = Time.getNewTime(2020, 3, 21, 11, 30);
		Time time4 = Time.getNewTime(2020, 3, 21, 11, 50);
		
		Timeslot timeslot1 = Timeslot.getNewTimeslot(time1, time2);
		Timeslot timeslot2 = Timeslot.getNewTimeslot(time2, time3);
		Timeslot timeslot3 = Timeslot.getNewTimeslot(time3, time4);
		Timeslot timeslot4 = Timeslot.getNewTimeslot("2020-03-21 10:30", "2020-03-21 11:00");
		
		assertEquals(timeslot1.toString().equals(timeslot4.toString()), true);
		
		assertEquals(timeslot1.islegal(), true);
		
		assertEquals(timeslot1.isConflict(timeslot2), true);
		assertEquals(timeslot1.isConflict(timeslot3), false);
		
		assertEquals(plan.addTimeslot(timeslot1), true);
		assertEquals(plan.addTimeslot(timeslot2), true);
		assertEquals(plan.addTimeslot(timeslot2), false);
		
		assertEquals(plan.getBegintime().toString().equals("2020-03-21 10:30"), true);
		assertEquals(plan.getEndtime().toString().equals("2020-03-21 11:30"), true);
	}
	
	
	
}
