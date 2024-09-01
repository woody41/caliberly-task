package com.caliberly.exceptions;

import com.caliberly.combinations.WinCombination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoWinningCombinationException extends Exception {
	WinCombination winCombination;
}
