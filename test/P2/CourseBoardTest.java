package P2;

import org.junit.Test;

import P2.CourseSchedule.CourseEntry;
import P3.Exception.MyException;

public class CourseBoardTest {
	//测试策略
	//测试能否正常将一节课的课表打印出来
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testBorad() throws MyException {
		CourseEntry plans = CourseEntry.getCourseEntry();
		String locationname1 = "正心1";
		String begintime1 = "2020-01-01 15:00";
		String endtime1 = "2020-01-01 18:00";
		String nowtime1 = "2020-01-01 14:30";
		
		plans.addLocation(locationname1);
		plans.addTeacher("A", "A380", "男", "教授");
		plans.addPlan("马原1", locationname1, begintime1, endtime1);
		plans.allocateResource("A", "马原1");
		
		plans.showBoard(nowtime1 ,locationname1);
	}
}
