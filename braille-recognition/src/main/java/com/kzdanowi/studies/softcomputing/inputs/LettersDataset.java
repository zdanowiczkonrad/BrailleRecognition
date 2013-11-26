/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.inputs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;

/**
 * Create input data for training from resource images
 */
public class LettersDataset {

	public static final String[] ALPHABET_LETTERS = alphabetLetters();
	public static final int CHAR_OFFSET = 'A';
	private List<List<Double>> cachedInputs;
	private List<List<Double>> cachedDistortedInputsWN;
	private List<List<Double>> cachedDistortedInputsVR;
	private List<List<Double>> cachedDistortedInputsHR;
	private List<List<Double>> cachedOutputs;

	public List<Double> getLetter(String letter) throws IOException {
		return Doubles
				.asList(ImageReader
						.getImage("src/main/java/com/kzdanowi/studies/softcomputing/inputs/data/"
								+ letter + ".jpg"));
	}

	public List<Double> getWhiteNoisedLetter(String letter) throws IOException {
		return Doubles
				.asList(ImageReader
						.getWhiteNoisedImage("src/main/java/com/kzdanowi/studies/softcomputing/inputs/data/"
								+ letter + ".jpg"));
	}

	public List<Double> getVerticallyRemovedLetter(String letter)
			throws IOException {
		return Doubles
				.asList(ImageReader
						.getVerticallyRemovedImage("src/main/java/com/kzdanowi/studies/softcomputing/inputs/data/"
								+ letter + ".jpg"));
	}

	public List<Double> getHorizontallyRemovedLetter(String letter)
			throws IOException {
		return Doubles
				.asList(ImageReader
						.getHorizontallyRemovedImage("src/main/java/com/kzdanowi/studies/softcomputing/inputs/data/"
								+ letter + ".jpg"));
	}

	public synchronized List<List<Double>> getInputs() throws IOException {

		if (cachedInputs == null) {
			precacheInputs();
		}
		return cachedInputs;
	}

	// DISTORTIONS

	public synchronized List<List<Double>> getWhiteNoisedInputs()
			throws IOException {

		if (cachedDistortedInputsWN == null) {
			precacheInputsWithWN();
		}
		return cachedDistortedInputsWN;
	}

	public synchronized List<List<Double>> getVerticallyRemovedInputs()
			throws IOException {

		if (cachedDistortedInputsVR == null) {
			precacheInputsWithVR();
		}
		return cachedDistortedInputsVR;
	}

	public synchronized List<List<Double>> getHorizontallyRemovedInputs()
			throws IOException {

		if (cachedDistortedInputsHR == null) {
			precacheInputsWithHR();
		}
		return cachedDistortedInputsHR;
	}

	private void precacheInputs() throws IOException {
		cachedInputs = new ArrayList<List<Double>>();

		for (String letter : ALPHABET_LETTERS) {
			cachedInputs.add(getLetter(letter));
			cachedInputs.add(getLetter(letter + "2"));
		}
	}

	// DISTORTIONS
	private void precacheInputsWithWN() throws IOException {
		cachedDistortedInputsWN = new ArrayList<List<Double>>();

		for (String letter : ALPHABET_LETTERS) {
			cachedDistortedInputsWN.add(getWhiteNoisedLetter(letter));
			cachedDistortedInputsWN.add(getWhiteNoisedLetter(letter + "2"));
		}
	}

	private void precacheInputsWithVR() throws IOException {
		cachedDistortedInputsVR = new ArrayList<List<Double>>();

		for (String letter : ALPHABET_LETTERS) {
			cachedDistortedInputsVR.add(getVerticallyRemovedLetter(letter));
			cachedDistortedInputsVR
					.add(getVerticallyRemovedLetter(letter + "2"));
		}
	}

	private void precacheInputsWithHR() throws IOException {
		cachedDistortedInputsHR = new ArrayList<List<Double>>();

		for (String letter : ALPHABET_LETTERS) {
			cachedDistortedInputsHR.add(getHorizontallyRemovedLetter(letter));
			cachedDistortedInputsHR.add(getHorizontallyRemovedLetter(letter
					+ "2"));
		}
	}

	private static String[] alphabetLetters() {

		String[] letters = new String[26];

		for (int i = 0; i < 26; i++) {
			char letter = (char) (CHAR_OFFSET + i);
			letters[i] = String.valueOf(letter);
		}
		return letters;

	}

	public synchronized List<List<Double>> getOutputs() {

		if (cachedOutputs == null) {
			precacheOutputs();
		}
		return cachedOutputs;
	}

	private void precacheOutputs() {
		cachedOutputs = new ArrayList<List<Double>>();

		for (int i = 0; i < 26; i++) {
			cachedOutputs.add(Lists.newArrayList(1. * i / 26));
			cachedOutputs.add(Lists.newArrayList(1. * i / 26));
		}
	}

	public String valueOf(Double d) {
		System.out.println("Double: " + d);
		return String
				.valueOf((char) (CHAR_OFFSET + ((int) Math.round(d * 26.))));
	}

}
