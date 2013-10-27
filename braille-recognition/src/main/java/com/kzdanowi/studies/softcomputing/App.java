package com.kzdanowi.studies.softcomputing;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.kzdanowi.studies.softcomputing.mlp.Constants;
import com.kzdanowi.studies.softcomputing.mlp.DefaultWeightGenerator;
import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;
import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.core.Layer;
import com.kzdanowi.studies.softcomputing.mlp.core.Network;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {
		try {
			init();
		} catch (InputOrWeightSizeException e) {
			System.out.println("There was an error trying to run network");
			e.printStackTrace();
		}
	}

	public static void init() throws InputOrWeightSizeException {
		/*
		 * sample run for the XOR problem
		 * 
		 * testing multiple output for the classification problem
		 * 
		 * two inputs - X and Y
		 * two outputs -T and F
		 * 
		 * xor(x,y)->(t,f):
		 * xor(0,0)->(0,1)
		 * xor(1,1)->(0,1)
		 * xor(0,1)->(1,0)
		 * xor(1,0)->(1,0)
		 * 
		 */
		Network network = createNetwork(2, 10, 2);

		List<List<Double>> in = new ArrayList<List<Double>>();
		List<List<Double>> out = new ArrayList<List<Double>>();

		in.add(newArrayList(0.0, 0.0));
		out.add(newArrayList(0.0, 1.0));
		in.add(newArrayList(0.1, 0.0));
		out.add(newArrayList(1.0, 0.0));
		in.add(newArrayList(0.0, 1.0));
		out.add(newArrayList(1.0, 0.0));
		in.add(newArrayList(1.0, 1.0));
		out.add(newArrayList(0.0, 1.0));

		train(network, in, out);
		test(network, in, out);

		int learningEpochs = 10000000;
		int checkpointAfterInteration = 100000;
		for (int j = 0; j < learningEpochs; j++) {

			train(network, in, out);
			if (j % checkpointAfterInteration == 0) {
				System.out.println("Learning progress: " + ((1. * j / learningEpochs) * 100) + "%");
				test(network, in, out);

			}
		}

		System.out.println("Traning ended. Results for running test against trained network:");
		test(network, in, out);
		//runUserConsoleVerifier(network, in, out);

	}

	private static Network createNetwork(int inputs, int hiddenNeurons, int outputs) {
		Network network = new Network();

		Random randomGenerator = new DefaultWeightGenerator();
		SigmoidActivationFunction activationFunction = new SigmoidActivationFunction();

		Double learningRate=0.25;
		Layer hiddenLayer = new Layer(hiddenNeurons, inputs, randomGenerator, activationFunction, learningRate);
		Layer outputLayer = new Layer(outputs, hiddenNeurons, randomGenerator, activationFunction, learningRate);

		network.addLayer(hiddenLayer);
		network.addLayer(outputLayer);
		return network;
	}

	private static void runUserConsoleVerifier(Network network, List<List<Double>> in, List<List<Double>> out)
			throws InputOrWeightSizeException {
		Scanner console = new Scanner(System.in);

		while (true) {
			System.out.println("Try network against data in format:");
			System.out.println("<input1> <input2> ... <inputn> <expectedOutput1> ... <expectedOutput2>");

			List<Double> userIn = new ArrayList<Double>();
			for (int i = 0; i < in.get(0).size(); i++) {
				userIn.add(Double.valueOf(console.nextDouble()));
			}

			List<Double> userExpected = new ArrayList<Double>();
			for (int i = 0; i < out.get(0).size(); i++) {
				userExpected.add(Double.valueOf(console.nextDouble()));
			}

			System.out.println("Network response for " + userIn);
			System.out.println(network.feedForward(userIn) + ", expected " + userExpected);
		}
	}

	private static void train(Network network, List<List<Double>> in, List<List<Double>> expected)
			throws InputOrWeightSizeException {
		checkArgument(in.size() == expected.size(), "Input vector has other size than output");

		for (int i = 0; i < in.size(); i++) {
			List<Double> actual = network.feedForward(in.get(i));
			List<Double> errors = arraySubstract(expected.get(i), actual);
			network.backPropagation(errors);

		}
	}

	private static void test(Network network, List<List<Double>> in, List<List<Double>> out) throws InputOrWeightSizeException {

		checkArgument(in.size() == out.size(), "Input vector has other size than output");

		for (int i = 0; i < in.size(); i++) {
			List<Double> networkOut = network.feedForward(in.get(i));
			System.out.println(" - expected: " + out.get(i) + " was: " + networkOut);
		}
	}

	private static List<Double> arraySubstract(List<Double> a, List<Double> b) {
		List<Double> sub = new ArrayList<Double>();
		checkArgument(a.size() == b.size(), "Unequal vector lengths");
		for (int i = 0; i < a.size(); i++) {
			sub.add(a.get(i) - b.get(i));
		}
		return sub;

	}
}
