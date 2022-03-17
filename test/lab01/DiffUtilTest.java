package lab01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiffUtilTest {

	@Test
	public void testAllSameNum() {
	  int arr[] = new int[] { 3, 3, 3 };
	  assertEquals(0, DiffUtil.findSmallestDiff(arr));
	}
		
	@Test
	public void testNegAndPos() {
	  int arr[] = new int[] { 52, 4, -8, 0, -17 };
	  assertEquals(4, DiffUtil.findSmallestDiff(arr));
	}
	
	@Test
	public void testTooSmallArray() {
	  int arr[] = new int[0];
	  assertThrows(IllegalArgumentException.class, () -> { DiffUtil.findSmallestDiff(arr); });
	}
	
	@Test
	public void testOneIsSmallestDiff() {
	  int arr[] = new int[] {-3, 9, 100, 45, 99, 105};
	  assertEquals(1, DiffUtil.findSmallestDiff(arr));
	}
	
	@Test
	public void testOverflowDiff() {
		int arr[] = new int[] {Integer.MAX_VALUE, -1};
		assertEquals(Integer.MIN_VALUE, DiffUtil.findSmallestDiff(arr));
	}
}
