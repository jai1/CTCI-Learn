package org.google;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		// CountingSheep.run(InputType.SMALL);
		// CountingSheep.run(InputType.LARGE);
		// RevengeOfThePancakes.run(InputType.SMALL);
		// RevengeOfThePancakes.run(InputType.LARGE);
		// Fractiles.run(InputType.SMALL);
		CoinJam coinJam = new CoinJam();
		coinJam.run(InputType.SMALL);
	}
	
}
