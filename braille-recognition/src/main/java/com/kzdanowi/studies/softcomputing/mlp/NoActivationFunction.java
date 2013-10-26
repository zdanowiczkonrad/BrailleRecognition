/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;


/**
 * Plain implementation returning output as its activation value
 */
public class NoActivationFunction implements ActivationFunction {

	public Double activate(Double output) {
		return output;
	}

	public Double getPropagationDelta(Double error, Double lastInput, Double lastOutput) {
		return 0.0;
	}

}
