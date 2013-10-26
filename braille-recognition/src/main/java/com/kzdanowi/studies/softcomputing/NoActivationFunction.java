/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing;

/**
 * Plain implementation returning output as its activation value
 */
public class NoActivationFunction implements ActivationFunction {

	public Double activate(Double output) {
		return output;
	}

}
