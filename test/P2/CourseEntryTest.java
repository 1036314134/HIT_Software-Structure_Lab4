package P2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import P2.CourseSchedule.CourseEntry;
import P3.Exception.MyException;

public class CourseEntryTest {
	//���Բ���
	//��addTeacher()������resourcesΪ��ʱ�ܷ��������룬��Ϊ��ʱ�ܷ��������룬�ܷ�����ظ�����
	//��removeTeacher()�������ܷ�����ɾ��
	//��addPlan()�������ܷ���������
	//��changeclassroom()�������ܷ������ı�
	//����State�࣬����״̬�ı��Ƿ����Ԥ��
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testResource(){
		CourseEntry courses = CourseEntry.getCourseEntry();
		assertEquals(courses.getResource("A"), null);
		try {
			courses.addTeacher("A", "320602200003212516", "��", "����");
			courses.addTeacher("B", "320602200003212516", "Ů", "��ʦ");
		}catch(MyException e) {
			assert false;
		}
		try {
			courses.addTeacher("B", "320602200003212516", "Ů", "��ʦ");
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
		String locationname1 = "����1";
		String locationname2 = "����2";
		String coursename1 = "Ӣ��";
		
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
		courses.addLocation("����1");
		courses.addPlan("Ӣ��", "����1", begintime1, endtime1);
		try {
			courses.cancel("Ӣ��");
		}catch(MyException e) {
			e.getMessage();
		}
		
		
		
	}
}
