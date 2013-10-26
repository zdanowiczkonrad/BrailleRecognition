/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import java.util.ArrayList;
import java.util.List;

/**
 * Network class holding layers and performing feed-forward and back-propagation
 */
public class Network {
	
	private final List<Layer> layers;

	public Network() {
		this.layers=new ArrayList<Layer>();
	}
	public List<Layer> getLayers() {
		return layers;
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
	}
	public List<Double> feedForward(List<Double> input) throws InputOrWeightSizeException {
		List<Double> toReturn=input;
		for (Layer each : layers) {
			toReturn=each.feedForward(toReturn);
		}
		return toReturn;
	}
	
	public void backPropagation(final Double error) {
		
		for(int i=layers.size()-1;i>=0;i--)
		{
			layers.get(i).backPropagate(error);
		}
		
	}
	@Override
	public String toString() {
		return "Network [layers=" + layers + "]";
	}
	
	

}
