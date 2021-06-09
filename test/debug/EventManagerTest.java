package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EventManagerTest {
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void testbook() {
		EventManager manager = new EventManager();
		
		assertEquals(manager.book(1, 10, 20), 1);
		assertEquals(manager.book(1, 1, 7), 1);
		assertEquals(manager.book(1, 10, 22), 2);
		assertEquals(manager.book(1, 5, 15), 3);
		assertEquals(manager.book(1, 5, 12), 4);
		assertEquals(manager.book(1, 7, 10), 4);
		
		assertEquals(manager.book(2, 10, 20), 4);
		assertEquals(manager.book(2, 1, 7), 4);
		
		assertEquals(manager.book(1, 0, 24), 5);
	}
}
