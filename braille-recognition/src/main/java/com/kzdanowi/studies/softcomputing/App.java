package com.kzdanowi.studies.softcomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kzdanowi.studies.softcomputing.mlp.*;

/**
 * Hello world!
 * 
 */
public class App {
	private static final int INPUT_NEURONS = 10;

	public static void main(String[] args) {
		try {
			init();
		} catch (InputOrWeightSizeException e) {
			System.out.println("There was an error trying to run network");
		}
	}

	public static void init() throws InputOrWeightSizeException {
		/*
		 * sample run, not yet with sense. TBD: Trainer.java, add Bias to network
		 */
		Network network = new Network();

		Random randomGenerator = new Random();
		SigmoidActivationFunction activationFunction = new SigmoidActivationFunction();

		Layer hiddenLayer = new Layer(INPUT_NEURONS, INPUT_NEURONS, randomGenerator, activationFunction);
		Layer outputLayer = new Layer(1, INPUT_NEURONS, randomGenerator, activationFunction);

		network.addLayer(hiddenLayer);
		network.addLayer(outputLayer);

		for (int i = 0; i < 10; i++) {
			network.feedForward(getRandomInput());
			network.backPropagation(randomGenerator.nextDouble());
		}
		System.out.println(network);
	}

	private static List<Double> getRandomInput() {
		List<Double> toReturn = new ArrayList<Double>();
		Random random = new Random();
		for (int i = 0; i < INPUT_NEURONS; i++) {

			toReturn.add(random.nextDouble());
		}
		return toReturn;
	}
}
