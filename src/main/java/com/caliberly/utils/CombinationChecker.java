package com.caliberly.utils;

import com.caliberly.combinations.GeneralWinCombination;

/**
 * Static util for combination checker
 */
public final class CombinationChecker {
	public static <T extends GeneralWinCombination> boolean checkCombination(T combination) {
		return true; // Assuming isWinning() is a method in WinCombination
	}

}
