/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {

	Double activate(Double input);

	Double getPropagationDelta(Double error, Double lastInput, Double lastOutput);

}
