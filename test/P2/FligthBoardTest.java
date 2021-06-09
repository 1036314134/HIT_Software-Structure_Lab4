package P2;

import org.junit.Test;

import P2.FlightSchedule.FlightEntry;
import P3.Exception.MyException;

public class FligthBoardTest {
	//测试策略
	//测试能否正常将航班管理界面打印出来
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testBorad() throws MyException {
		FlightEntry plans = FlightEntry.getFlightEntry();
		String locationname1 = "harbin";
		String locationname2 = "nantong";
		String begintime1 = "2020-01-01 15:00";
		String endtime1 = "2020-01-01 18:00";
		String nowtime1 = "2020-01-01 14:30";
		
		plans.addLocation(locationname1);
		plans.addLocation(locationname2);
		plans.addPlane("A", "A380", 100, 10);
		plans.addPlan("航班1", locationname1, locationname2, begintime1, endtime1);
		plans.allocateResource("A", "航班1");
		
		plans.showBoard(nowtime1 ,locationname1);
		
		
		
	}

}
