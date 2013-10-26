/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class modelling perceptron with multiple inputs, corresponding weights and
 * one output
 */
public class Perceptron {


	private static final Double LEARNING_RATE = 0.25;
	private final List<Double> weights;
	private ActivationFunction activationFunction = new NoActivationFunction();
	private Double lastOutput = 0.0;
	private List<Double> lastInputs = new ArrayList<Double>();

	/**
	 * Test constructor taking weights instead of autogenerating them
	 */
	public Perceptron(final List<Double> weights) {
		this.weights = weights;
		setlastInputsToZero(weights.size());
	}

	/**
	 * Takes number of inputs and random generator
	 */
	public Perceptron(int inputSize, final Random randomGenerator) {
		this.weights = new ArrayList<Double>();
		for (int i = 0; i < inputSize; i++) {
			weights.add(randomGenerator.nextDouble());
		}
		setlastInputsToZero(inputSize);
	}

	private void setlastInputsToZero(int inputSize) {
		for (int i = 0; i < inputSize; i++) {
			lastInputs.add(0.0);
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
		lastInputs = inputs;

		for (int i = 0; i < inputs.size(); i++) {
			output += inputs.get(i) * weights.get(i);
		}

		lastOutput = activationFunction.activate(output);
		return lastOutput;
	}

	private static void assertInputArraysLengthEqual(int inputsLength, int weightsLength) throws InputOrWeightSizeException {
		if (inputsLength > weightsLength)
			throw new InputOrWeightSizeException("The length of inputs is larger than the length of weights");
		else if (inputsLength < weightsLength)
			throw new InputOrWeightSizeException("The length of weights is larger than the length of inputs");
	}

	public int getInputSize() {
		return weights.size();
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void updateWeights(Double error) {
		for (int i = 0; i < weights.size(); i++) {
			Double difference = LEARNING_RATE * activationFunction.getPropagationDelta(error, lastInputs.get(i), lastOutput);
			weights.set(i, weights.get(i) + difference);
		}
	}

	@Override
	public String toString() {
		return "Perceptron [weights=" + weights + "]\n";
	}
}
