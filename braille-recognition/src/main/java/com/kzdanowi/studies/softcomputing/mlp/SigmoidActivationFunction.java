/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import com.kzdanowi.studies.softcomputing.mlp.core.ActivationFunction;

/**
 * Sigmoid activation function implementation
 */
public class SigmoidActivationFunction implements ActivationFunction {

	private static final long serialVersionUID = 1887608595542939363L;

	public Double activate(Double input) {
		return 1 / (1 + Math.exp(-input));
	}

	public Double getPropagationDelta(Double error, Double lastInput, Double lastOutput) {

		return error * lastInput * lastOutput * (1.0 - lastOutput);
	}

}
