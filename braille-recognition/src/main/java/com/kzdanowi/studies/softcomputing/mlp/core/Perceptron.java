/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kzdanowi.studies.softcomputing.mlp.ActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;
import com.kzdanowi.studies.softcomputing.mlp.NoActivationFunction;

/**
 * Class modelling perceptron with multiple inputs, corresponding weights and
 * one output
 */
public class Perceptron {

	private Double learningRate;
	private final List<Double> weights;
	private ActivationFunction activationFunction = new NoActivationFunction();
	private Double lastOutput = 0.0;
	private List<Double> lastInputs = new ArrayList<Double>();
	private Double bias;

	/**
	 * Test constructor taking weights instead of autogenerating them
	 */
	public Perceptron(final List<Double> weights) {
		this.weights = weights;
	}

	/**
	 * Sets bias weight
	 */
	public void setBias(Double bias)
	{
		this.bias=bias;
	}
	/**
	 * Takes number of inputs and random generator
	 */
	public Perceptron(int inputSize, final Random randomGenerator) {
		this.weights = new ArrayList<Double>();
		for (int i = 0; i < inputSize; i++) {
			weights.add(randomGenerator.nextDouble());
		}
		bias=randomGenerator.nextDouble();
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
			output = output + inputs.get(i) * weights.get(i);
		}
		
		output+=1*bias;

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
			Double difference = learningRate * activationFunction.getPropagationDelta(error, lastInputs.get(i), lastOutput);
			weights.set(i, weights.get(i) + difference);
		}
		bias+=learningRate*activationFunction.getPropagationDelta(error, 1.0, lastOutput);
	}

	@Override
	public String toString() {
		return "Perceptron [weights=" + weights + "]\n";
	}

	public void setLearningRate(Double learningRate) {
		this.learningRate=learningRate;
	}
}
