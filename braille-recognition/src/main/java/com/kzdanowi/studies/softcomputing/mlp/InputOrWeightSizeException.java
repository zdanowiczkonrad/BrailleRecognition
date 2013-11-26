/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

/**
 * Exception when the size of weights does not match size of inputs
 */
public class InputOrWeightSizeException extends Exception {

	private static final long serialVersionUID = -3763278719933050220L;

	public InputOrWeightSizeException(String message) {
		super(message);
	}
}
