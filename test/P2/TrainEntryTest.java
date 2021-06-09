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
	//���Բ���
	//��addTrain()������resourcesΪ��ʱ�ܷ���룬��Ϊ��ʱ�ܷ���룬�ܷ�����ظ�����
	//��removeResource()�������ܷ�����ɾ��
	//��getResource()�������ܷ�������ȡ��Դ
	//��addLocation()������locationΪ��ʱ�ܷ��������룬��Ϊ��ʱ�ܷ���룬�ܷ�����ظ�����
	//��getLocation()�������ܷ�������ȡλ��
	//��removeLocation()�������ܷ�����ɾ��λ��
	//��addPlan()������վ��δ�����ܷ����·�ߣ�վ���Ѽ����ܷ����·�ߣ��ܷ�����ظ�����
	//��State�࣬���Ը���״̬�ı��Ƿ����Ԥ��
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
			plans.addPlan("·��1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		
		try {
			plans.addLocation(locationname1);
			plans.addLocation(locationname2);
			plans.addLocation(locationname3);
			plans.addPlan("·��1", locations, times);
			plans.addTrain("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("·��1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		
		PlanningEntry<Train> line1 = plans.getPlan("·��1");
		
		assertEquals(line1.getState().getName(), "WAITING");
		assertEquals(line1.getLocations().get(0).getName(), locationname1);
		assertEquals(line1.getLocations().get(1).getName(), locationname2);
		assertEquals(line1.getLocations().get(2).getName(), locationname3);
		assertEquals(line1.getTimeslots().get(0).getBegin().toString(), "2020-01-01 17:00");
		assertEquals(line1.getTimeslots().get(0).getEnd().toString(), "2020-01-01 19:00");
		assertEquals(line1.getTimeslots().get(1).getBegin().toString(), "2020-01-01 20:00");
		assertEquals(line1.getTimeslots().get(1).getEnd().toString(), "2020-01-01 22:00");
		 
		assertEquals(plans.removePlan("·��1"), true);
		assertEquals(plans.getPlan("·��1"), null);
		
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
			plans.addPlan("·��1", locations, times);
			plans.addTrain("A", "A380", 100, 10);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.addPlan("·��1", locations, times);
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getPlan("·��1").getState().getName(), "WAITING");
		try {
			plans.start("·��1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.allocateResource("A", "·��1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getPlan("·��1").getState().getName(), "ALLOCATED");
		try {
			plans.start("·��1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getPlan("·��1").getState().getName(), "RUNNING");
		try {
			plans.complete("·��1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getPlan("·��1").getState().getName(), "ENDED");
		
	
		
	}
	
	
	
	
	
	
	

}
