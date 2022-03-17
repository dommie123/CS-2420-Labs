package lab02;

import java.util.ArrayList;
import java.util.List;

public class SquareRootTimingExperiment {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		long lastTime = startTime;
		List<Long> times = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			long currentTime = System.nanoTime();
			//startTime = System.nanoTime();
			Math.sqrt(i+1);
			times.add(currentTime - lastTime);
			lastTime = currentTime;
		}
		for (int i = 0; i < 10; i++) {
			System.out.println("It took " + times.get(i) + " ns to calculate the square root of " + (i+1) + ".");
		}
	}

}
