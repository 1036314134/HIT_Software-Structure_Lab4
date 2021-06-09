package P2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import P2.CourseSchedule.CourseEntry;
import P3.Exception.MyException;

public class CourseEntryTest {
	//测试策略
	//对addTeacher()，测试resources为空时能否正常加入，不为空时能否正常加入，能否避免重复加入
	//对removeTeacher()，测试能否正常删除
	//对addPlan()，测试能否正常加入
	//对changeclassroom()，测试能否正常改变
	//对于State类，测试状态改变是否符合预期
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testResource(){
		CourseEntry courses = CourseEntry.getCourseEntry();
		assertEquals(courses.getResource("A"), null);
		try {
			courses.addTeacher("A", "320602200003212516", "男", "教授");
			courses.addTeacher("B", "320602200003212516", "女", "讲师");
		}catch(MyException e) {
			assert false;
		}
		try {
			courses.addTeacher("B", "320602200003212516", "女", "讲师");
		}catch(MyException e) {
			assert true;
		}
		
		assertEquals(courses.getResource("A").getName(), "A");
		try {
			courses.removeResource("A");
		}catch(MyException e) {
			e.getMessage();
		}
		
		assertEquals(courses.getResource("A"), null);
	}
	
	@Test
	public void testLocation() throws MyException {
		CourseEntry courses = CourseEntry.getCourseEntry();
		String begintime1 = "2020-01-01 17:00";
		String endtime1 = "2020-01-01 19:00";
		String locationname1 = "正心1";
		String locationname2 = "正心2";
		String coursename1 = "英语";
		
		courses.addTeacher("A", "101", "man", "teacher");
		courses.addLocation(locationname1);
		courses.addLocation(locationname2);
		
		courses.addPlan(coursename1, locationname1, begintime1, endtime1);
		assertEquals(courses.getPlan(coursename1).getLocations().get(0).getName(), locationname1);
		try {
			courses.changeclassroom(coursename1, locationname2);
		}catch(MyException e) {
			e.getMessage();
		}
		assertEquals(courses.getPlan(coursename1).getLocations().get(0).getName(), locationname2);
		
		
		
	}
	
	@Test
	public void testState() throws MyException {
		CourseEntry courses = CourseEntry.getCourseEntry();
		String begintime1 = "2020-01-01 17:00";
		String endtime1 = "2020-01-01 19:00";
		
		courses.addTeacher("A", "101", "man", "teacher");
		courses.addLocation("正心1");
		courses.addPlan("英语", "正心1", begintime1, endtime1);
		try {
			courses.cancel("英语");
		}catch(MyException e) {
			e.getMessage();
		}
		
		
		
	}
}
