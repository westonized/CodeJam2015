import java.util.HashMap;

import javax.swing.text.html.HTML.Tag;

public class C {
	private final String keyboard;
	private HashMap<String, Integer> frequencies = new HashMap<String, Integer>();
	private int maxBananas;
	private int overlap;

	public C(String keyboard) {
		this.keyboard = keyboard;
		calcKeyboardFrequencies(keyboard);
	}

	public double chance(String target, int s) {
		if (!containsAllChars(target, keyboard))
			return 0;
		overlap = shortestOverlap(target);
		maxBananas = bestCaseForMonkey(overlap, target, s);

		double totalBananas = 0;
		double chanceOfTarget = getChanceOfWord(target);
		double chanceOfOverlap = getChanceOfWord(target.substring(overlap));
		for (int b = 1; b <= maxBananas; b++) {
			double chanceOfHittingB = (s - target.length() + 1)
					* chanceOfHittingB(b, target, chanceOfTarget);
			totalBananas += b * chanceOfHittingB;
		}

		// double chanceOfOverlap = getChanceOfWord(target.substring(overlap));
		// double chanceOfTarget = getChanceOfWord(target);
		// double combined = chanceOfTarget
		// * Math.pow(chanceOfOverlap, maxBananas - 1);
		return maxBananas - totalBananas / maxBananas;
	}

	private double chanceOfHittingB(int b, String target, double chanceOfTarget) {
		return Math.pow(chanceOfTarget, b);
	}

	private void calcKeyboardFrequencies(String keyboard) {
		for (int i = 0; i < keyboard.length(); i++) {
			String key = "" + keyboard.charAt(i);
			Integer integer = frequencies.get(key);
			if (integer == null)
				frequencies.put(key, 1);
			else
				frequencies.put(key, integer + 1);
		}
	}

	public double getChanceOfWord(String word) {
		double chance = 1;
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			chance *= chanceOfLetter(letter);
		}
		return chance;
	}

	private double chanceOfLetter(char letter) {
		return frequencies.get("" + letter) / (double) keyboard.length();
	}

	public static int shortestOverlap(String string) {
		for (int i = 1; i < string.length(); i++) {
			if ((string.substring(0, i) + string).startsWith(string))
				return i;
		}
		return string.length();
	}

	public static int bestCaseForMonkey(int overlap, String target, int s) {
		if (overlap == -1)
			overlap = shortestOverlap(target);
		s = s - target.length();
		if (s < 0)
			return 0;
		return s / overlap + 1;
	}

	public boolean containsAllChars(String target, String keyboard) {
		for (int i = 0; i < target.length(); i++)
			if (keyboard.indexOf(target.charAt(i)) == -1)
				return false;
		return true;
	}
}
