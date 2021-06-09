package debug;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LowestPriceTest {
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testShopping() {
		LowestPrice temp = new LowestPrice();
		List<Integer> price = new ArrayList<>();
		List<List<Integer>> special = new ArrayList<>();
		List<Integer> needs = new ArrayList<>();

		price.add(2);
		price.add(5);
		needs.add(3);
		needs.add(2);
		

		assertEquals(temp.shopping(price, special, needs),16);
		
		List<Integer> specialA = new ArrayList<>();
		specialA.add(3);
		specialA.add(0);
		specialA.add(5);
		special.add(specialA);
		
		List<Integer> specialB = new ArrayList<>();
		specialB.add(1);
		specialB.add(2);
		specialB.add(10);
		special.add(specialB);
		
		
		assertEquals(temp.shopping(price, special, needs), 14);
		
	}
}
