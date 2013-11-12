/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.inputs;

import org.fest.assertions.Condition;

/**
 * Condition for a number that is in a given range
 */
final class InRangeCondition extends Condition<Double> {
	private double leftCondition;
	private double rightCondition;

	public InRangeCondition(double leftCondition, double rightCondition) {
		this.leftCondition = leftCondition;
		this.rightCondition = rightCondition;	
	}
	
	@Override
	public boolean matches(Double value) {
		return value>=leftCondition && value<=rightCondition;
	}
}