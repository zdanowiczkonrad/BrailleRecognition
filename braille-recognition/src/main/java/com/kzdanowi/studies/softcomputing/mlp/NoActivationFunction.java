/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import com.kzdanowi.studies.softcomputing.mlp.core.ActivationFunction;


/**
 * Plain implementation returning output as its activation value
 */
public class NoActivationFunction implements ActivationFunction {

	private static final long serialVersionUID = -1013592956297287730L;

	public Double activate(Double output) {
		return output;
	}

	public Double getPropagationDelta(Double error, Double lastInput, Double lastOutput) {
		return 0.0;
	}

}
