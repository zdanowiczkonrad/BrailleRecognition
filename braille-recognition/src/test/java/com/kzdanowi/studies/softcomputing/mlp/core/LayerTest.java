/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;

import org.fest.assertions.Delta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;
import com.kzdanowi.studies.softcomputing.mlp.NoActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;

/**
 * Test for layer class
 */
public class LayerTest {

	private static final int INPUTS = 10;
	private static final int PERCEPTRONS = 100;
	private final WeightGenerator mockWeightGenerator = Mockito.mock(WeightGenerator.class);
	private final ActivationFunction activationFunction = new SigmoidActivationFunction();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldContainCorrespondingPerceptronsNumber() {
		// given

		// when
		Layer layer = new Layer(PERCEPTRONS, 0, mockWeightGenerator, activationFunction, Constants.DEFAULT_LEARNING_RATE);

		// then
		assertThat(layer.getPerceptrons()).isNotNull();
		assertThat(layer.getPerceptrons()).hasSize(PERCEPTRONS);

	}

	@Test
	public void createdPerceptronsShouldHaveCorrespondingInputsNumber() {
		// given

		// when
		Layer layer = new Layer(PERCEPTRONS, INPUTS, mockWeightGenerator, activationFunction, Constants.DEFAULT_LEARNING_RATE);

		// then
		for (Perceptron each : layer.getPerceptrons()) {
			assertThat(each.getInputSize()).isEqualTo(INPUTS);
		}
	}

	@Test
	public void shouldOutputZeroForZeroInputAndWeights() throws InputOrWeightSizeException {
		// given
		when(mockWeightGenerator.next()).thenReturn(0.0);
		Layer layer = new Layer(PERCEPTRONS, INPUTS, mockWeightGenerator, new NoActivationFunction(), Constants.DEFAULT_LEARNING_RATE);

		// when
		List<Double> output = layer.feedForward(newArrayList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));

		// then
		assertThat(output).hasSize(100);

		for (Double each : output) {
			assertThat(each).isEqualTo(0);
		}
	}

	@Test
	public void shouldOutputAppropriateValuesForSimpleInputs() throws InputOrWeightSizeException {
		/*
		 * Layer equivalent to the first hidden layer from example
		 * http://www.nnwj.de/backpropagation.html
		 */

		// given
		when(mockWeightGenerator.next()).thenReturn(0.62).thenReturn(0.55).thenReturn(0.).thenReturn(0.42).thenReturn(-0.17).thenReturn(0.);
		Layer layer = new Layer(2, 2, mockWeightGenerator, activationFunction, Constants.DEFAULT_LEARNING_RATE);

		// when
		List<Double> actual = layer.feedForward(newArrayList(0.0, 1.0));
		List<Double> expected = newArrayList(0.634135591, 0.457602059);

		// then
		assertDoubleArraysEqualWithDelta(actual, expected, delta(0.000000001));
	}

	@Test
	public void shouldOutputAppropriateValuesForAnotherSimpleOutputs() throws InputOrWeightSizeException {
		/*
		 * Layer equivalent to the output layer from example
		 * http://www.nnwj.de/backpropagation.html
		 */

		// given
		when(mockWeightGenerator.next()).thenReturn(0.35).thenReturn(0.81).thenReturn(0.);
		Layer layer = new Layer(1, 2, mockWeightGenerator, activationFunction, Constants.DEFAULT_LEARNING_RATE);

		// when
		List<Double> actual = layer.feedForward(newArrayList(0.634135591, 0.457602059));
		List<Double> expected = newArrayList(0.643962658);

		// then
		assertDoubleArraysEqualWithDelta(actual, expected, delta(0.000000001));
	}

	@Test
	public void shouldUpdateWeightsBasedOnTheExpectedOutputValue() throws InputOrWeightSizeException {
		/*
		 * Layer equivalent to the output layer from example
		 * http://www.nnwj.de/backpropagation.html
		 */

		// given
		when(mockWeightGenerator.next()).thenReturn(0.35).thenReturn(0.81).thenReturn(0.);
		Layer layer = new Layer(1, 2, mockWeightGenerator, activationFunction, Constants.DEFAULT_LEARNING_RATE);

		// when
		List<Double> calculated = layer.feedForward(newArrayList(0.634135591, 0.457602059));
		List<Double> error;
		error = newArrayList(0.0 - calculated.get(0));
		layer.backPropagateEach(error);
		List<List<Double>> weights = layer.getWeights();

		// then
		assertDoubleArraysEqualWithDelta(weights.get(0), newArrayList(0.326593362, 0.793109407), delta(0.000000001));
	}

	private void assertDoubleArraysEqualWithDelta(List<Double> actual, List<Double> expected, Delta delta) {
		if (actual.size() != expected.size()) {
			fail("Actual has different size than expected");
		}
		for (int i = 0; i < actual.size(); i++) {
			assertThat(actual.get(i)).isEqualTo(expected.get(i), delta);
		}
	}

}
