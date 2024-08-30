package com.caliberly.enums;

import lombok.Getter;

@Getter
public enum CombinationWhen {
	LINEAR_SYMBOL("linear_symbols"),
	SAME_SYMBOL("same_symbols");

	private final String name;

	CombinationWhen(String name) {
		this.name = name;
	}

	public static CombinationWhen fromString(String type) {
		if (type == null) {
			return null;
		}
		for (CombinationWhen combinationWhen : CombinationWhen.values()) {
			if (combinationWhen.getName().equalsIgnoreCase(type)) {
				return combinationWhen;
			}
		}
		throw new IllegalArgumentException("Unknown symbol type: " + type);
	}
}
