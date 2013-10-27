/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Trainer {

	private Network network;

	public Trainer(Network network) {
		this.network = network;
	}

	public Network getNetwork() {
		return network;
	}

	public List<Double> test(List<Double> input) {
		return network.feedForward(input);
	}

	public List<Double> getError(List<Double> expected, List<Double> actual) {
		return arraySubstract(expected,actual);
	}
	
	private List<Double> arraySubstract(List<Double> a, List<Double> b) {
		List<Double> sub = new ArrayList<Double>();
		checkArgument(a.size() == b.size(), "Unequal vector lengths");
		for (int i = 0; i < a.size(); i++) {
			sub.add(a.get(i) - b.get(i));
		}
		return sub;
	}

	public void train(List<Double> input, List<Double> expectedOutput) {
		List<Double> lastTestResult = test(input);
		List<Double> outputErrors = getError(expectedOutput,lastTestResult);
		network.backPropagation(outputErrors);
	}

	private Double meanSquaredError(List<Double> outputErrors) {
		Double toReturn=0.0;
		for (Double each : outputErrors) {
			toReturn+=Math.pow(each, 2);
		}
		return toReturn;
	}

	public Double getNetError(List<Double> outputErrors) {
		return meanSquaredError(outputErrors);
	}


}
