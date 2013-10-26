/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

/**
 * Sigmoid activation function implementation
 */
public class SigmoidActivationFunction implements ActivationFunction {

	public Double activate(Double input) {
		return 1 / (1 + Math.exp(-input));
	}

	public Double getPropagationDelta(Double error, Double lastInput, Double lastOutput) {
		return error * lastInput * lastOutput * (1 - lastOutput);
	}

}
