package P2;

import org.junit.Test;

import P2.CourseSchedule.CourseEntry;
import P3.Exception.MyException;

public class CourseBoardTest {
	//���Բ���
	//�����ܷ�������һ�ڿεĿα��ӡ����
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	
	@Test
	public void testBorad() throws MyException {
		CourseEntry plans = CourseEntry.getCourseEntry();
		String locationname1 = "����1";
		String begintime1 = "2020-01-01 15:00";
		String endtime1 = "2020-01-01 18:00";
		String nowtime1 = "2020-01-01 14:30";
		
		plans.addLocation(locationname1);
		plans.addTeacher("A", "A380", "��", "����");
		plans.addPlan("��ԭ1", locationname1, begintime1, endtime1);
		plans.allocateResource("A", "��ԭ1");
		
		plans.showBoard(nowtime1 ,locationname1);
	}
}
