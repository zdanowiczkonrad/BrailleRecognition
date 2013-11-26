package com.kzdanowi.studies.softcomputing.mlp.core;

import java.io.Serializable;

public class SingleHiddenLayerNetwork implements Serializable {

	private static final long serialVersionUID = 8245892761420636573L;

	private int inputsCount;
	private int hiddenNeuronsCount;
	private int outputsCount;

	private double[] lastInputs;
	private double[] lastHiddenNeuronsValues;
	private double[] lastOutputs;

	private double[][] hiddenLayerWeights;
	private double[][] outputLayerWeights;

	private double learningRate = 0.5;

	public SingleHiddenLayerNetwork(int inputsCount, int hiddenNeuronsCount, int outputsCount) {

		this.inputsCount = inputsCount;
		this.hiddenNeuronsCount = hiddenNeuronsCount;
		this.outputsCount = outputsCount;

		lastInputs = new double[inputsCount + 1];
		lastHiddenNeuronsValues = new double[hiddenNeuronsCount + 1];
		lastOutputs = new double[outputsCount + 1];

		hiddenLayerWeights = new double[hiddenNeuronsCount + 1][inputsCount + 1];
		outputLayerWeights = new double[outputsCount + 1][hiddenNeuronsCount + 1];

		initializeWeights();
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	private void initializeWeights() {

		for (int j = 1; j <= hiddenNeuronsCount; j++)
			for (int i = 0; i <= inputsCount; i++) {
				hiddenLayerWeights[j][i] = Math.random() - 0.5;
			}

		for (int j = 1; j <= outputsCount; j++)
			for (int i = 0; i <= hiddenNeuronsCount; i++) {
				outputLayerWeights[j][i] = Math.random() - 0.5;
			}
	}

	public double[] train(double[] input, double[] expected) {
		double[] actual = feedForward(input);
		backPropagate(expected);
		return actual;
	}

	public double[] feedForward(double[] input) {

		updateLastInputs(input);

		setBias();

		feedForwardHiddenLayer();
		feedForwardOutputLayer();

		return lastOutputs;
	}

	private void updateLastInputs(double[] input) {
		for (int i = 0; i < inputsCount; i++) {
			lastInputs[i + 1] = input[i];
		}
	}

	private void feedForwardOutputLayer() {
		for (int j = 1; j <= outputsCount; j++) {
			lastOutputs[j] = 0.0;
			for (int i = 0; i <= hiddenNeuronsCount; i++) {
				lastOutputs[j] += outputLayerWeights[j][i] * lastHiddenNeuronsValues[i];
			}
			lastOutputs[j] = 1.0 / (1 + 0 + Math.exp(-lastOutputs[j]));
		}
	}

	private void feedForwardHiddenLayer() {
		for (int j = 1; j <= hiddenNeuronsCount; j++) {
			lastHiddenNeuronsValues[j] = 0.0;
			for (int i = 0; i <= inputsCount; i++) {
				lastHiddenNeuronsValues[j] += hiddenLayerWeights[j][i] * lastInputs[i];
			}
			lastHiddenNeuronsValues[j] = 1.0 / (1.0 + Math.exp(-lastHiddenNeuronsValues[j]));
		}
	}

	private void setBias() {
		lastInputs[0] = 1.0;
		lastHiddenNeuronsValues[0] = 1.0;
	}

	public void backPropagate(double[] expected) {

		double[] outputLayerError = new double[outputsCount + 1];
		double[] hiddenLayerError = new double[hiddenNeuronsCount + 1];

		backPropagateOutputLayer(expected, outputLayerError);
		backPropagateHiddenLayer(outputLayerError, hiddenLayerError);

		updateOutputLayer(outputLayerError);
		updateHiddenLayer(hiddenLayerError);
	}

	private void updateHiddenLayer(double[] hiddenLayerError) {
		for (int j = 1; j <= hiddenNeuronsCount; j++)
			for (int i = 0; i <= inputsCount; i++)
				hiddenLayerWeights[j][i] += learningRate * hiddenLayerError[j] * lastInputs[i];
	}

	private void updateOutputLayer(double[] outputLayerError) {
		for (int j = 1; j <= outputsCount; j++)
			for (int i = 0; i <= hiddenNeuronsCount; i++)
				outputLayerWeights[j][i] += learningRate * outputLayerError[j] * lastHiddenNeuronsValues[i];
	}

	private void backPropagateHiddenLayer(double[] outputLayerError, double[] hiddenLayerError) {
		double error = 0.0;
		for (int i = 0; i <= hiddenNeuronsCount; i++) {
			for (int j = 1; j <= outputsCount; j++)
				error += outputLayerWeights[j][i] * outputLayerError[j];

			hiddenLayerError[i] = lastHiddenNeuronsValues[i] * (1.0 - lastHiddenNeuronsValues[i]) * error;
			error = 0.0;
		}
	}

	private void backPropagateOutputLayer(double[] expected, double[] outputLayerError) {
		for (int i = 1; i <= outputsCount; i++)
			outputLayerError[i] = lastOutputs[i] * (1.0 - lastOutputs[i]) * (expected[i - 1] - lastOutputs[i]);
	}

}