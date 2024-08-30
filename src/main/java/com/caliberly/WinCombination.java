package com.caliberly;

import com.caliberly.enums.CombinationWhen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class WinCombination {

	@Setter
	private String name;
	//	private double reward_multiplier;
//	private int count;
	@Setter
	private CombinationWhen when;
//	private String group;
//	private String covered_areas;
}
