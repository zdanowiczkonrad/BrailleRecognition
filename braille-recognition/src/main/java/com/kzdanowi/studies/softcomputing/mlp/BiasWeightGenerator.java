/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.Random;

/**
 * Weight generator for Bias
 */
public class BiasWeightGenerator extends Random {

	private static final long serialVersionUID = -1118512428248950645L;

	@Override
	public double nextDouble() {
		return 1.0;
	}

	
}
