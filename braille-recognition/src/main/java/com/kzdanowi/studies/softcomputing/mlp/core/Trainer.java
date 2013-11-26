/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.util.List;

/**
 * 
 */
public interface Trainer {

	public abstract Network getNetwork();

	public abstract List<Double> test(List<Double> input);

	public abstract List<Double> getError(List<Double> expected, List<Double> actual);

	public abstract void train(List<Double> input, List<Double> expectedOutput);

	public abstract Double getNetError(List<Double> outputErrors);
	
	void saveToPath(String path);
	
	void loadFromPath(String path);
	
}