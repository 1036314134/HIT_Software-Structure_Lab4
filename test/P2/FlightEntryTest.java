package P2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import P1.PlanningEntry;
import P2.FlightSchedule.FlightEntry;
import P2.FlightSchedule.Plane;
import P3.Exception.MyException;

public class FlightEntryTest {
	//���Բ���
	//��addTrain()������resourcesΪ��ʱ�ܷ���룬��Ϊ��ʱ�ܷ���룬�ܷ�����ظ�����
	//��removeResource()�������ܷ�����ɾ��
	//��getResource()�������ܷ�������ȡ��Դ
	//��addLocation()������locationΪ��ʱ�ܷ��������룬��Ϊ��ʱ�ܷ���룬�ܷ�����ظ�����
	//��getLocation()�������ܷ�������ȡλ��
	//��removeLocation()�������ܷ�����ɾ��λ��
	//��addPlan()�����Ի���δ�����ܷ���뺽�࣬�����Ѽ����ܷ���뺽�࣬�ܷ�����ظ�����
	//��State�࣬���Ը���״̬�ı��Ƿ����Ԥ��
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
			plans.addPlan("����1", locationname1, locationname2, begintime1, endtime1);
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
			plans.addPlan("����1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("����1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert true;
		}
		
		PlanningEntry<Plane> line1 = plans.getPlan("����1");
		
		assertEquals(line1.getState().getName(), "WAITING");
		assertEquals(line1.getLocations().get(0).getName(), locationname1);
		assertEquals(line1.getLocations().get(1).getName(), locationname2);
		assertEquals(line1.getTimeslots().get(0).getBegin().toString(), "2020-01-01 17:00");
		assertEquals(line1.getTimeslots().get(0).getEnd().toString(), "2020-01-01 19:00");
		
		assertEquals(plans.removePlan("����1"), true);
		assertEquals(plans.getPlan("����1"), null);
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
			plans.addPlan("����1", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		try {
			plans.addPlan("����2", locationname1, locationname2, begintime1, endtime1);
		}catch(MyException e) {
			assert false;
		}
		
		try {
			plans.start("����1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.complete("����1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.allocateResource("A", "����1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("����1").getName(), "ALLOCATED");
		try {
			plans.start("����1");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("����1").getName(), "RUNNING");
		try {
			plans.cancel("����1");
		}catch(MyException e) {
			assert true;
		}
		try {
			plans.complete("����1");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getState("����1").getName(), "ENDED");
		try {
			plans.allocateResource("A", "����2");
		}catch(MyException e) {
			assert true;
		}
		assertEquals(plans.getState("����2").getName(), "ALLOCATED");
		try {
			plans.cancel("����2");
		}catch(MyException e) {
			assert false;
		}
		assertEquals(plans.getState("����2").getName(), "CANCELLED");
		
	}
}
