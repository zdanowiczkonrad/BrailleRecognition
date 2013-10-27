/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.Random;

/**
 * Adjusted random generator returning random double from (-1,1)
 */
public class DefaultWeightGenerator extends Random {

	private static final long serialVersionUID = 6052282608160661294L;

	@Override
	public double nextDouble() {
		return 1.0-(super.nextDouble()*2.0);
		//return super.nextDouble();
	}

	
}
