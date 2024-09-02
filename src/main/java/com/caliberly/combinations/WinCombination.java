package com.caliberly.combinations;

import com.caliberly.enums.CombinationWhen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WinCombination implements GeneralWinCombination {

	@Setter
	private String name;
	//	private double reward_multiplier;
	private int count = 0;
	@Setter
	private CombinationWhen when;
	private String group; //not necessary, but loaded
	private List<List<String>> covered_areas;
}