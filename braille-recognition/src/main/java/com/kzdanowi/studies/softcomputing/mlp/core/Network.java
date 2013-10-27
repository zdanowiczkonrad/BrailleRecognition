/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kzdanowi.studies.softcomputing.mlp.InputOrWeightSizeException;

/**
 * Network class holding layers and performing feed-forward and back-propagation
 */
public class Network implements Serializable {

	private static final long serialVersionUID = -4303431906287786966L;
	
	private final List<Layer> layers;

	public Network() {
		this.layers = new ArrayList<Layer>();
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	public List<Double> feedForward(List<Double> input) {
		try {
			return doFeedForward(input);
		} catch (InputOrWeightSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Double> doFeedForward(List<Double> input) throws InputOrWeightSizeException {
		List<Double> toReturn = input;
		for (Layer each : layers) {
			toReturn = each.feedForward(toReturn);
		}
		return toReturn;
	}

	public void backPropagation(final List<Double> error) {
		Layer outputLayer = layers.get(layers.size() - 1);
		outputLayer.backPropagate(error);
		
		for (int i = layers.size() - 2; i >= 0; i--) {
			layers.get(i).backPropagateEach(error);
		}

	}
	
	public static void serializeNetwork(Network network, String networkSerializedPath) {
		try {
			File f = new File(networkSerializedPath);
			f.getParentFile().mkdirs();
			FileOutputStream fileOut = new FileOutputStream(networkSerializedPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(network);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static Network deserializeNetworkFrom(String networkSerializedPath) {
		Network deserialized = null;
		try {
			FileInputStream fileIn = new FileInputStream(networkSerializedPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deserialized = (Network) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Network class not found.");
			c.printStackTrace();
		}
		return deserialized;
	}

}
