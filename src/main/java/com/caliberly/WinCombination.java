package com.caliberly;

import com.caliberly.enums.CombinationWhen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.util.List;

@AllArgsConstructor
@Getter
public class WinCombination {

	@Setter
	private String name;
	//	private double reward_multiplier;
	private int count = 0;
	@Setter
	private CombinationWhen when;
	private String group; //not necessary, but loaded
	private List<List<String>> covered_areas;
}
