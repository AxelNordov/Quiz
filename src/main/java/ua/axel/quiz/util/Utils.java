package ua.axel.quiz.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class Utils {
	public static final Random rand;

	static {
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			class MyOwnNoSuchAlgorithmException extends RuntimeException {
				public MyOwnNoSuchAlgorithmException(NoSuchAlgorithmException e) {
					super(e);
				}
			}
			throw new MyOwnNoSuchAlgorithmException(e);
		}
	}

	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> T getRandom(List<T> list) {
		return list.get(rand.nextInt(list.size()));
	}
}
