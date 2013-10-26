/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing;

/**
 * Signum activation function implementation
 */
public class SignumActivationFunction implements ActivationFunction {

	public Double activate(Double input) {
		return input>=0.0 ? 1.0 : -1.0;
	}
	

}
