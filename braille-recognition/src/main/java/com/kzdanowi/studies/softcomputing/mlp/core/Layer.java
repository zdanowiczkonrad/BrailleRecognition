/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;



/**
 * Layer class allowing to feed-forward and back-propagate
 */
public class Layer implements Serializable {

	private static final long serialVersionUID = -5842874270448182017L;
	
	private ArrayList<Perceptron> perceptrons;
	
	public Layer(int perceptronsInLayer, int perceptronInputs,WeightGenerator randomGenerator, ActivationFunction activationFunction, Double learningRate) {
		perceptrons = new ArrayList<Perceptron>();	
		for(int i=0;i<perceptronsInLayer;i++)
		{
			Perceptron perceptron = new Perceptron(perceptronInputs, randomGenerator);
			perceptron.setActivationFunction(activationFunction);
			perceptron.setLearningRate(learningRate);
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
	public List<List<Double>> getWeights() {
		List<List<Double>> weights = new ArrayList<List<Double>>();
		for (Perceptron each : perceptrons) {
			weights.add(each.getWeights());
		}
		return weights;
	}

	/**
	 * Propagate each error over a network
	 */
	public void backPropagateEach(List<Double> error) {
		for(Double each : error)
		{
			backPropagate(each);
		}
	}
	/**
	 * Used by output layer when learning of each perceptron should be performed
	 * Separately per each error value
	 */
	public void backPropagate(List<Double> error) {
		for(int i=0;i<perceptrons.size();i++)
		{
			perceptrons.get(i).updateWeights(error.get(i));
		}
	}

	/**
	 * Propagate an error learning over a network
	 */
	private void backPropagate(Double error) {
		for(int i=0;i<perceptrons.size();i++)
		{
			perceptrons.get(i).updateWeights(error);
		}
	}	
}
