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


	public DefaultWeightGenerator() {
	}
	public Double next() {
		//return random.nextDouble();
		return (Math.random()*10)-5;
	}

	
}
