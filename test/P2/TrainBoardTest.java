package P2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import P2.TrainSchedule.TrainEntry;
import P3.Exception.MyException;

public class TrainBoardTest {
	//测试策略
	//测试能否正常将高铁管理界面打印出来
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testBorad() throws MyException {
		TrainEntry plans = TrainEntry.getTrainEntry();
		String locationname1 = "nantong";
		String locationname2 = "wuhu";
		String locationname3 = "hangzhou";
		String time1 = "2020-01-01 17:00";
		String time2 = "2020-01-01 19:00";
		String time3 = "2020-01-01 20:00";
		String time4 = "2020-01-01 22:00";
		String nowtime1 = "2020-01-01 17:30";
		List<String> locations = new ArrayList<String>();
		locations.add(locationname1);
		locations.add(locationname2);
		locations.add(locationname3);
		List<String> times = new ArrayList<String>();
		times.add(time1);
		times.add(time2);
		times.add(time3);
		times.add(time4);
		
		plans.addLocation(locationname1);
		plans.addLocation(locationname2);
		plans.addLocation(locationname3);
		
		plans.addTrain("A", "A380", 100, 10);
		plans.addPlan("路线1", locations, times);
		plans.allocateResource("A", "路线1");
		
		
		plans.showBoard(nowtime1 ,locationname1);
		
		
		
	}
}
