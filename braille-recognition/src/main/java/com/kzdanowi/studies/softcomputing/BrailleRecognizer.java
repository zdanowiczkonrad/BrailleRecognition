package com.kzdanowi.studies.softcomputing;

import java.io.IOException;
import java.util.List;

import com.kzdanowi.studies.softcomputing.inputs.LettersDataset;
import com.kzdanowi.studies.softcomputing.mlp.DefaultWeightGenerator;
import com.kzdanowi.studies.softcomputing.mlp.SigmoidActivationFunction;
import com.kzdanowi.studies.softcomputing.mlp.core.Layer;
import com.kzdanowi.studies.softcomputing.mlp.core.Network;
import com.kzdanowi.studies.softcomputing.mlp.core.TrainRunner;
import com.kzdanowi.studies.softcomputing.mlp.core.Trainer;
import com.kzdanowi.studies.softcomputing.mlp.core.WeightGenerator;

public class BrailleRecognizer {

	public static void main(String[] args) throws IOException {

		String path = "/networks/net_Braille.bin";
		Network network = Network.deserializeNetworkFrom(path);

		LettersDataset dataset = new LettersDataset();

		List<List<Double>> in = dataset.getInputs();
		List<List<Double>> in2 = dataset.getVerticallyRemovedInputs();
		List<List<Double>> in3 = dataset.getHorizontallyRemovedInputs();
		List<List<Double>> in4 = dataset.getWhiteNoisedInputs();
		List<List<Double>> out = dataset.getOutputs();

		for (int i = 0; i < in.size(); i++) {
			System.out.println("Input: distracted letter "
					+ dataset.valueOf(out.get(i).get(0)));
			System.out.println("Recognized output: letter "
					+ dataset.valueOf(network.feedForward(in2.get(i)).get(0)));
		}

		/*
		 * System.out.println("Testing of distorted images starts:");
		 * 
		 * for (List<Double> each : in) { System.out.println(each);
		 * System.out.println(dataset
		 * .valueOf(network.feedForward(each).get(0))); }
		 * 
		 * System.out.println("After applying WHITE NOISE");
		 * 
		 * for (List<Double> each : in4) { System.out.println(each);
		 * System.out.println(dataset
		 * .valueOf(network.feedForward(each).get(0))); }
		 * 
		 * Network network = createNetwork(256, 50, 1);
		 * 
		 * LettersDataset dataset = new LettersDataset();
		 * 
		 * List<List<Double>> in = dataset.getInputs(); List<List<Double>> out =
		 * dataset.getOutputs();
		 * 
		 * TrainRunner runner = new TrainRunner(new Trainer(network));
		 * 
		 * System.out.println("Training starts."); List<Double> run =
		 * runner.run(in, out, 0.001);
		 * 
		 * System.out.println("Training ended. Epochs=" + run.size() +
		 * ". Now testing:");
		 * 
		 * for (List<Double> each : in) { System.out.println(each);
		 * System.out.println(network.feedForward(each)); }
		 * 
		 * String path = "/networks/net_Braille.bin";
		 * 
		 * Network.serializeNetwork(network, path);
		 * 
		 * System.out.println("Net saved to file " + path);
		 */
	}

	@SuppressWarnings("unused")
	private static Network createNetwork(int inputs, int hiddenNeurons,
			int outputs) {
		Network network = new Network();

		WeightGenerator randomGenerator = new DefaultWeightGenerator(-2., 2.);
		SigmoidActivationFunction activationFunction = new SigmoidActivationFunction();

		Double learningRate = 0.25;

		Layer hiddenLayer = new Layer(hiddenNeurons, inputs, randomGenerator,
				activationFunction, learningRate);
		Layer outputLayer = new Layer(outputs, hiddenNeurons, randomGenerator,
				activationFunction, learningRate);

		network.addLayer(hiddenLayer);
		network.addLayer(outputLayer);

		return network;
	}

}
