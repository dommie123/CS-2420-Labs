package lab03;

/**
 * This class contains code for completing the Lab 3: Debugging activities.
 * 
 * @author CS 2420 course staff
 * @version January 28, 2022
 */
public class Part2 {

	public static void infiniteLoops() {
		int a = 1, b = 0, c = -1, d = 3, f = 5;

		//Loop #1
		while(a < 10) {
			a += d;
			d -= c;
			a++;
			try {
				Thread.sleep(100);
			} 
			catch(InterruptedException e) { }
		}

		//Loop #2
		while(b < 10) {
			b *= a;	// b is not getting incremented here. 
			a += c;
			d++;
			try {
				Thread.sleep(100);
			} 
			catch(InterruptedException e) { }
		}

		//Loop #3
		while(a > c) {
			a += d;
			d--;
			b++;
			try {
				Thread.sleep(100);
			} 
			catch(InterruptedException e) { }
		}

		//Loop #4
		while(f > 0) {
			f -= c; // Here, c switches from -1 to 1, causing f to switch between 5 and 4.
			b += c;
			c *= -1;
			try {
				Thread.sleep(100);
			} 
			catch(InterruptedException e) { }
		}
	}
}
