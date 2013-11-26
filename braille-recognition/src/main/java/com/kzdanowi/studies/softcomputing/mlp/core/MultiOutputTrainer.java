/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Doubles;

public class MultiOutputTrainer implements Trainer, Serializable {

	private static final long serialVersionUID = 904173000929064436L;
	private SingleHiddenLayerNetwork mlp;

	public MultiOutputTrainer(int inputs,int hidden,int outputs,double learningRate) {
		mlp = new SingleHiddenLayerNetwork(inputs,hidden,outputs);
		mlp.setLearningRate(learningRate);
	}
	
	public Network getNetwork() {
		return null;
	}

	public List<Double> test(List<Double> input) {
		List<Double> output = Doubles.asList(mlp.feedForward(Doubles.toArray(input)));
		List<Double> outWithoutBias = output.subList(1, output.size());
		return outWithoutBias;
	}

	public List<Double> getError(List<Double> expected, List<Double> actual) {
		return arraySubstract(actual, expected);
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
		mlp.train(Doubles.toArray(input), Doubles.toArray(expectedOutput));
	}

	private Double meanSquaredError(List<Double> outputErrors) {
		Double toReturn = 0.0;
		for (Double each : outputErrors) {
			toReturn += Math.pow(each, 2);
		}
		return toReturn;
	}

	public Double getNetError(List<Double> outputErrors) {
		return meanSquaredError(outputErrors);
	}

	public void saveToPath(String path) {
		try {
			File f = new File(path);
			f.getParentFile().mkdirs();
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(mlp);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void loadFromPath(String path) {
		SingleHiddenLayerNetwork deserialized = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deserialized = (SingleHiddenLayerNetwork) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Network class not found.");
			c.printStackTrace();
		}
		this.mlp=deserialized;
	}

}
