package com.kzdanowi.studies.softcomputing;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.kzdanowi.studies.softcomputing.mlp.DefaultWeightGenerator;
import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.core.Layer;
import com.kzdanowi.studies.softcomputing.mlp.core.Network;
import com.kzdanowi.studies.softcomputing.mlp.core.TrainRunner;
import com.kzdanowi.studies.softcomputing.mlp.core.Trainer;
import com.kzdanowi.studies.softcomputing.mlp.core.WeightGenerator;

/**
 * sample run for the XOR problem
 * 
 * testing multiple output for the classification problem
 * 
 * two inputs - X and Y one output - 1 or 0
 * 
 * xor(x,y)->(T), xor(0,0)->(0), xor(0,1)->(1), xor(1,0)->(1), xor(1,1)->(0)
 */
public class App {

	public static void mains(String[] args) {
		Network network = createNetwork(2, 3, 1);
		

		List<List<Double>> in = generateXorInputs();
		List<List<Double>> out = generateXorOutputs();

		TrainRunner runner = new TrainRunner(new Trainer(network));

		System.out.println("Training starts.");
		List<Double> run = runner.run(in, out, 0.001);
		System.out.println("Training ended. Epochs=" + run.size() + ". Now testing:");

		for (List<Double> each : in) {
			System.out.println(each);
			System.out.println(network.feedForward(each));
		}

		String path = "/networks/net_XOR.bin";
		Network.serializeNetwork(network, path);
		System.out.println("Net saved to file " + path);

	}

	private static List<List<Double>> generateXorInputs() {
		List<List<Double>> in = new ArrayList<List<Double>>();
		in.add(newArrayList(0.1, 0.0));
		in.add(newArrayList(0.0, 1.0));
		in.add(newArrayList(0.0, 0.0));
		in.add(newArrayList(1.0, 1.0));
		return in;
	}

	private static List<List<Double>> generateXorOutputs() {
		List<List<Double>> out = new ArrayList<List<Double>>();
		out.add(newArrayList(1.0));
		out.add(newArrayList(1.0));
		out.add(newArrayList(0.0));
		out.add(newArrayList(0.0));
		return out;
	}

	private static Network createNetwork(int inputs, int hiddenNeurons, int outputs) {
		Network network = new Network();

		WeightGenerator randomGenerator = new DefaultWeightGenerator(-5.,5.);
		SigmoidActivationFunction activationFunction = new SigmoidActivationFunction();

		Double learningRate = 0.25;

		Layer hiddenLayer = new Layer(hiddenNeurons, inputs, randomGenerator, activationFunction, learningRate);
		Layer outputLayer = new Layer(outputs, hiddenNeurons, randomGenerator, activationFunction, learningRate);

		network.addLayer(hiddenLayer);
		network.addLayer(outputLayer);

		return network;
	}

}
