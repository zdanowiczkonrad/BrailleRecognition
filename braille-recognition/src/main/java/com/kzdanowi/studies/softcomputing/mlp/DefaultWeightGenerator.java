/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.Random;

import com.kzdanowi.studies.softcomputing.mlp.core.WeightGenerator;

/**
 * Adjusted random generator returning random double from (-1,1)
 */
public class DefaultWeightGenerator implements WeightGenerator {

	private Random random;

	public DefaultWeightGenerator() {
		this.random=new Random();
	}
	public Double next() {
		return random.nextDouble();
		//return 1.0-(random.nextDouble()*2.0);
	}

	
}
