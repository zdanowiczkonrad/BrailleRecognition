/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

/**
 * 
 */
public interface ActivationFunction {

	Double activate(Double input);

	Double getPropagationDelta(Double error, Double lastInput, Double lastOutput);

}