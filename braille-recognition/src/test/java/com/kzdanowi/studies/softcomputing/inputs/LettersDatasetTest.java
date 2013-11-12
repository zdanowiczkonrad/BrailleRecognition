/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.inputs;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Condition;
import org.junit.Before;
import org.junit.Test;

public class LettersDatasetTest {

	private static final Double DELTA = 0.0000000000000001;
	private LettersDataset lettersDataset;

	@Before
	public void setUp() {
		lettersDataset = new LettersDataset();		
	}
	@Test
	public void shouldCreateListOfInputLetters() throws Exception {
	
		// given
		List<Double> aLetter = new ArrayList<Double>();

		// when
		aLetter = lettersDataset.getLetter("A");

		// then
		assertThat(aLetter).hasSize(16 * 16);
	}

	@Test
	public void shouldReturnDatasetInputsOfAnExpectedLength() throws Exception {
		
		// when
		List<List<Double>> inputs = lettersDataset.getInputs();

		// then
		assertThat(inputs).hasSize(52);
	}

	@Test
	public void shouldReturnDatasetOutputsOfAnExpectedLength() {

		// when
		List<List<Double>> outputs = lettersDataset.getOutputs();

		// then
		assertThat(outputs).hasSize(52);
	}

	@Test
	public void datasetOutputShouldContainEveryElementDuplicated() {

		/**
		 * Every output class twice in the order:
		 * 
		 * {A A} {B B} ... {Z Z}
		 * 
		 * as the dataset is expected to have two images for each letter
		 */
		
		// when
		List<List<Double>> actual = lettersDataset.getOutputs();

		// then
		assertThatEachOutputIsDuplicated(actual);
	}

	@Test
	public void datasetOutputsShouldBeNormalized() {

		// when
		List<List<Double>> actual = lettersDataset.getOutputs();

		// then
		assertThatEachOutputIsNormalized(actual);
	}

	@Test
	public void normalizedOutputShouldBeProperlyTranslatedToLetters() {
		
		// given
		List<List<Double>> outputs = lettersDataset.getOutputs();

		// when
		Double[] actual = getPlainArrayFrom(outputs);
		String[] expected = LettersDataset.ALPHABET_LETTERS;

		// then
		for (int i = 0; i < actual.length; i++) {
			assertThat(lettersDataset.valueOf(actual[i])).isEqualTo(expected[i / 2]);
		}
	}

	@Test
	public void translationShouldRoundValuesProperly() {

		// given

		/**
		 * Based on formula formula: letterX = indexOfLetterX / 26
		 * 
		 * for example indexOfLetterC = 3 , then letterC = 3 / 26
		 */
		Double letterA = 0.0;
		Double letterB = 0.038461538461538464;
		Double letterC = 0.07692307692307693;
		/**
		 * Distance between any two outputs
		 */
		Double interval = getMeanInterval(letterA, letterB, letterC);
		
		// when
		Double letterB_closeToA = letterB - (interval / 2) + DELTA;
		Double letterB_closeToC = letterB + (interval / 2) - DELTA;

		Double letterA_closeToB = letterB - (interval / 2) - DELTA;
		Double letterC_closeToB = letterB + (interval / 2) + DELTA;

		Double letterB_or_letterA = letterB - (interval / 2);
		Double letterB_or_letterC = letterB + (interval / 2);

		// then
		assertThat(lettersDataset.valueOf(letterB_closeToA)).isEqualTo("B");
		assertThat(lettersDataset.valueOf(letterB_closeToC)).isEqualTo("B");

		/* edge cases */
		assertThat(lettersDataset.valueOf(letterA_closeToB)).isEqualTo("A");
		assertThat(lettersDataset.valueOf(letterC_closeToB)).isEqualTo("C");

		assertThat(lettersDataset.valueOf(letterB_or_letterA)).isIn("B", "A");
		assertThat(lettersDataset.valueOf(letterB_or_letterC)).isIn("B", "C");
	}
	
	@Test
	public void inputDataShouldBePrecached() throws Exception {

		// when
		Object inputsFirstInvocation = lettersDataset.getInputs();
		Object inputsSecondInvocation = lettersDataset.getInputs();
		
		// then
		assertThat(inputsFirstInvocation).overridingErrorMessage("The objects <inputs from first invocation> and <inputs from second invocation> have difference references.").isSameAs(inputsSecondInvocation);
	}
	
	@Test
	public void outputDataShouldBePrecached() throws Exception {

		// when
		Object outputsFirstInvocation = lettersDataset.getOutputs();
		Object outputsSecondInvocation = lettersDataset.getOutputs();
		
		// then
		assertThat(outputsFirstInvocation).overridingErrorMessage("The objects <outputs from first invocation> and <outputs from second invocation>have different references.").isSameAs(outputsSecondInvocation);
	}
	
	private Double getMeanInterval(Double output_letterA, Double output_letterB, Double output_letterC) {
		return mean(mean(output_letterA, output_letterB), mean(output_letterB, output_letterC));
	}

	private Double mean(Double... inputs) {
		Double out = 0.0;
		for (Double each : inputs) {
			out += each;
		}
		return out / inputs.length;
	}

	private Double[] getPlainArrayFrom(List<List<Double>> outputs) {
		Double[] toReturn = new Double[outputs.size()];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = outputs.get(i).get(0);
		}

		return toReturn;
	}

	private void assertThatEachOutputIsNormalized(List<List<Double>> actual) {
		Condition<Double> inNormalizedRange = new InRangeCondition(0., 1.);
		for (List<Double> outputList : actual) {
			for (Double eachOutput : outputList) {
				assertThat(eachOutput).is(inNormalizedRange);
			}
		}
	}

	private void assertThatEachOutputIsDuplicated(final List<List<Double>> outputs) {
		for (int i = 0; i < outputs.size(); i += 2) {
			assertThat(outputs.get(i)).isEqualTo(outputs.get(i + 1));
		}
	}
}
