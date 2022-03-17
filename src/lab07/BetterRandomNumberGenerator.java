package lab07;

public class BetterRandomNumberGenerator implements RandomNumberGenerator {
	
	private long seed;
	private long const1;	// the multiplier
	private long const2;	// the increment
	
	public BetterRandomNumberGenerator() {
		seed = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
		}
		const1 = (System.nanoTime() - seed)  * 69420;
		for (int i = 0; i < 10000; i++) {
		}
		const2 = seed + System.nanoTime() / 777;
	}

	@Override
	public int nextInt(int max) {
		// TODO Auto-generated method stub
		return (int) (((const1 * seed) + const2) % max);
	}

	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}

	@Override
	public void setConstants(long const1, long const2) {
		this.const1 = const1;
		this.const2 = const2;
	}
}
