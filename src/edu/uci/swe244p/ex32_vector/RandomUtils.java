package edu.uci.swe244p.ex32_vector;

import java.util.Random;

public class RandomUtils {
	public static String randomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}

	public static int randomInteger() {
		Random r = new Random();
		int Low = 0;
		int High = 100;
		int Result = r.nextInt(High - Low) + Low;
		return Result;
	}
}
