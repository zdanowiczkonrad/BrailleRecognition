/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Delta;
import org.junit.Test;

import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;

public class SigmoidActivationFunctionTest {

	@Test
	public void sigmoidActivationFunctionShouldReturnCorrectValue() throws Exception {
		// given
		ActivationFunction activationFunction = new SigmoidActivationFunction();

		// then
		assertThat(activationFunction.activate(0.5)).isEqualTo(Double.valueOf(0.6224),Delta.delta(0.0001));
	}
}
