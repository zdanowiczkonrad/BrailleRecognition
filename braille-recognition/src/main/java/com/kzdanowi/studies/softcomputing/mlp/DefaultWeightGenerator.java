/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import com.kzdanowi.studies.softcomputing.mlp.core.WeightGenerator;

/**
 * Adjusted random generator returning random double from (-100,100)
 */
public class DefaultWeightGenerator implements WeightGenerator {

	private Double from=-1.0;
	private Double to=1.0;

	public DefaultWeightGenerator() {
	}

	public DefaultWeightGenerator(Double from, Double to) {
		this.from=from;
		this.to=to;
	}

	public Double next() {
		double nextRand = (Math.random() * (to-from)) +from;
		return nextRand;
	}

}
