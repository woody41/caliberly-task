package com.caliberly.combinations;

import com.caliberly.enums.CombinationWhen;

import java.util.List;

/**
 * Standard rule
 */
public interface GeneralWinCombination {

	String getName();

	int getCount();

	double getReward_multiplier();

	CombinationWhen getWhen();

	String getGroup();

	List<List<String>> getCovered_areas();
}
