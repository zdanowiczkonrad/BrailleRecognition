/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class modelling perceptron with multiple inputs, corresponding weights and
 * one output
 */
public class Perceptron {

	private List<Double> weights;
	private ActivationFunction activationFunction = new NoActivationFunction();

	/**
	 * Test constructor taking weights instead of autogenerating them
	 */
	public Perceptron(final List<Double> weights) {
		this.weights = weights;
	}

	/**
	 * Takes number of inputs and random generator
	 */
	public Perceptron(int inputSize, final Random randomGenerator) {
		this.weights = new ArrayList<Double>();
		for (int i = 0; i < inputSize; i++) {
			weights.add(randomGenerator.nextDouble());
		}
	}

	/**
	 * Set activation function that is going to be used by the perceptron
	 */
	public void setActivationFunction(final ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;

	}

	/**
	 * Perform feedforward on inputs
	 */
	public Double feedForward(final List<Double> inputs) throws InputOrWeightSizeException {

		assertInputArraysLengthEqual(inputs.size(), weights.size());

		Double output = 0.0;

		for (int i = 0; i < inputs.size(); i++) {
			output += inputs.get(i) * weights.get(i);
		}

		return activationFunction.activate(output);
	}

	private static void assertInputArraysLengthEqual(int inputsLength, int weightsLength) throws InputOrWeightSizeException {
		if (inputsLength > weightsLength)
			throw new InputOrWeightSizeException("The length of inputs is larger than the length of weights");
		else if (inputsLength < weightsLength)
			throw new InputOrWeightSizeException("The length of weights is larger than the length of inputs");
	}

}
