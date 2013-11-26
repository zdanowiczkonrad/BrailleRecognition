/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import java.util.List;

/**
 * 
 */
public interface Network {

	public abstract List<Double> feedForward(List<Double> input);

	public abstract void backPropagation(List<Double> error);

}