package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fest.assertions.Delta;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

import com.kzdanowi.studies.softcomputing.mlp.ActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;
import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.SignumActivationFunction;

import com.kzdanowi.studies.softcomputing.mlp.core.Perceptron;



public class PerceptronTest {

	@Test
	public void shouldCreateAPerceptronWithOneInputAndOneOutput() {
		// given
		List<Double> weights = newArrayList();

		// when
		Perceptron perceptron = new Perceptron(weights);

		// then
		assertThat(perceptron).isNotNull();
	}

	@Test
	public void shouldCountOutputBasedOnSingleInput() throws Exception {
		// given
		List<Double> inputs = newArrayList(1.0);
		List<Double> weights = inputs;
		Perceptron perceptron = new Perceptron(weights);
		perceptron.setBias(0.);
		
		// when
		Double output = perceptron.feedForward(inputs);
		
		// then
		assertThat(output).isEqualTo(1.0);
	}
	
	@Test
	public void shouldCountOutputBasedOnMultipleInputs() throws Exception {
		// given
		List<Double> inputs = newArrayList(1.0,2.0);
		List<Double> weights = newArrayList(0.5,-0.25);
		Perceptron perceptron = new Perceptron(weights);
		perceptron.setBias(0.);
		// when
		Double output = perceptron.feedForward(inputs);
		
		// then
		assertThat(output).isEqualTo(0);	
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void expectInputOrWeightSizeExceptionWhenInputArrayLargerThanWeights() throws Exception {
		// given 
		List<Double> inputs = newArrayList(0.0,0.0);
		List<Double> weights = newArrayList(0.0);
		Perceptron perceptron = new Perceptron(weights);
		
		// expect
		thrown.expect(InputOrWeightSizeException.class);
		thrown.expectMessage(JUnitMatchers.containsString("The length of inputs is larger than the length of weights"));
		
		// when
		perceptron.feedForward(inputs);
	}
	
	@Test
	public void expectInputOrWeightSizeExceptionWhenWeightArrayLargerThanInputs() throws Exception {
		// given 
		List<Double> inputs = newArrayList(0.0);
		List<Double> weights = newArrayList(0.0,0.0);
		Perceptron perceptron = new Perceptron(weights);
		
		// expect
		thrown.expect(InputOrWeightSizeException.class);
		thrown.expectMessage(JUnitMatchers.containsString("The length of weights is larger than the length of inputs"));
		
		// when
		perceptron.feedForward(inputs);
	}
	
	@Test
	public void shouldReturnOutputBasedOnSignumActivationFunction() throws Exception {
		// given
		ActivationFunction signumActivationFunction = new SignumActivationFunction();

		List<Double> inputs = newArrayList(1.0);
		Perceptron perceptron = new Perceptron(newArrayList(0.5));
		perceptron.setActivationFunction(signumActivationFunction);
		perceptron.setBias(0.);
		
		// when
		Double output = perceptron.feedForward(inputs);
		
		// then
		assertThat(output).isEqualTo(1.0);
	}
	
	@Test
	public void shouldRandomizeWeightsAndCountEquivalentOutput() throws Exception {
		// given
		Random randomGeneratorMock = mock(Random.class);
		when(randomGeneratorMock.nextDouble()).thenReturn(0.1).thenReturn(0.2).thenReturn(0.3).thenReturn(0.);
		
		ArrayList<Double> inputs = newArrayList(1.0,2.0,3.0);
		Perceptron perceptron = new Perceptron(3,randomGeneratorMock);
		
		// when
		Double output = perceptron.feedForward(inputs);
		
		// then
		assertThat(output).isEqualTo(1.4);
	}
	
	@Test
	public void shouldProperActivatePerceptronBasedOnConcreteValues() throws Exception {
		
		/*
		 * as on the first layer in example
		 * http://www.nnwj.de/backpropagation.html
		 */
		
		// given	
		Perceptron perceptron1=new Perceptron(newArrayList(0.62,0.55));
		Perceptron perceptron2=new Perceptron(newArrayList(0.42,-0.17));
		
		perceptron1.setActivationFunction(new SigmoidActivationFunction());
		perceptron1.setBias(0.0);

		perceptron2.setActivationFunction(new SigmoidActivationFunction());
		perceptron2.setBias(0.0);
		
		// when
		Double output1=perceptron1.feedForward(newArrayList(0.0,1.0));
		Double output2=perceptron2.feedForward(newArrayList(0.0,1.0));
		
		//then
		assertThat(output1).isEqualTo(0.634135591,Delta.delta(0.000000001));
		assertThat(output2).isEqualTo(0.457602059,Delta.delta(0.000000001));
	}

}
