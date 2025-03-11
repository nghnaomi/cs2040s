import java.util.*;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	Map<String, Map<Character, Integer>> words = new HashMap<>();
	// prefix, next char, num of times that char appeared
	// total possibilities is length of first hash
	private int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		for (int i = 0; i < text.length() - order; i++) {
			String key = text.substring(i, i + order);
			char nextKey = text.toCharArray()[i + order];
			words.putIfAbsent(key, new HashMap<>());
			Map<Character, Integer> next = words.get(key);
			next.put(nextKey, next.getOrDefault(nextKey, 0) + 1);
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (!words.containsKey(kgram)) {
			return 0;
		} else {
			Map<Character, Integer> next = words.get(kgram);
			int total = 0;
			for (int v : next.values()) {
				total += v;
			}
			return total;
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (!words.containsKey(kgram)) {
			return 0;
		} else {
			Map<Character, Integer> next = words.get(kgram);
			if (!next.containsKey(c)) {
				return 0;
			}
			return next.get(c);
		}
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		int total = getFrequency(kgram);
		if (total == 0) {
			return NOCHARACTER;
		}
		int random = generator.nextInt(total);
		Map<Character, Integer> next = words.get(kgram);
		if (next == null) {
			return NOCHARACTER;
		}

		List<Character> sorted = new ArrayList<>(next.keySet());
		Collections.sort(sorted);

		for (char c : sorted) {
			int curr = next.get(c);
			random -= curr;
			if (random < 0) {
				return c;
			}
		}
		return NOCHARACTER;
	}
}
