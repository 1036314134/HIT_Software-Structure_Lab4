package P1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeTest {
	//���Բ���
	//��isLegalTime()�������ܷ���ȷ�ж�
	//��sameday()�������ܷ���ȷ�ж�
	//��equals()�����Ե��벻���������
	//��compareto()���������ڡ����ڡ������������
	//��addtime()������ʱ���Ƿ���������
	
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testaddTime() {
		Time time1 = Time.getNewTime("2020-01-01 18:00");
		Time time2 = Time.getNewTime("2020-01-01 19:00");
		
		assertEquals(Time.isLegalTime("2020-01-01 19:00"), true);
		assertEquals(time1.sameday(time2), true);
		
		assertEquals(time1.equals(time2), false);
		assertEquals(time1.compareto(time2), -1);
		
		Time time3 = Time.getNewTime("0000-00-00 1:00");
		
		time1.addTime(time3);
		
		assertEquals(time1.toString().equals(time2.toString()), true);
		assertEquals(time1.compareto(time2), 0);
		
		Time time4 = Time.getNewTime("2020-01-01 15:00");
		Time time5 = Time.getNewTime("2020-01-01 15:30");

		assertEquals(time4.compareto(time5), -1);
		
		time4.addTime(time3);
		
		assertEquals(time4.compareto(time5), 1);
		
		
		
	}
}
