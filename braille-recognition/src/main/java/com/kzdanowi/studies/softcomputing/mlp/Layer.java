/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Layer class allowing to feed-forward and back-propagate
 */
public class Layer {

	private ArrayList<Perceptron> perceptrons;

	public Layer(int perceptronsInLayer, int perceptronInputs,Random randomGenerator, ActivationFunction activationFunction) {
		perceptrons = new ArrayList<Perceptron>();	
		for(int i=0;i<perceptronsInLayer;i++)
		{
			Perceptron perceptron = new Perceptron(perceptronInputs, randomGenerator);
			perceptron.setActivationFunction(activationFunction);
			perceptrons.add(perceptron);
		}
	}

	public List<Perceptron> getPerceptrons() {
		return perceptrons;
	}

	public List<Double> feedForward(List<Double> input) throws InputOrWeightSizeException {
		List<Double> output = new ArrayList<Double>();
		for (Perceptron perceptron : perceptrons) {
			output.add(perceptron.feedForward(input));
		}
		return output;
	}

	public void backPropagate(Double error) {
		for(int i=0;i<perceptrons.size();i++)
		{
			perceptrons.get(i).updateWeights(error);
		}
	}

	public List<List<Double>> getWeights() {
		List<List<Double>> weights = new ArrayList<List<Double>>();
		for (Perceptron each : perceptrons) {
			weights.add(each.getWeights());
		}
		return weights;
	}

	@Override
	public String toString() {
		return "Layer [perceptrons=" + perceptrons + "]";
	}


}
