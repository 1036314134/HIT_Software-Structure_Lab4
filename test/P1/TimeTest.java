package P1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeTest {
	//测试策略
	//对isLegalTime()，测试能否正确判断
	//对sameday()，测试能否正确判断
	//对equals()，测试等与不等两种情况
	//对compareto()，测试早于、等于、晚于三种情况
	//对addtime()，测试时间是否正常增加
	
	
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
