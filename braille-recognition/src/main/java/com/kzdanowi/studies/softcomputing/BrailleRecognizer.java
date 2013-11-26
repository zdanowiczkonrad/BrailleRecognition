package com.kzdanowi.studies.softcomputing;

import java.io.IOException;
import java.util.List;

import com.kzdanowi.studies.softcomputing.inputs.LettersDataset;
import com.kzdanowi.studies.softcomputing.mlp.core.MultiOutputTrainer;
import com.kzdanowi.studies.softcomputing.mlp.core.TrainRunner;
import com.kzdanowi.studies.softcomputing.mlp.core.Trainer;

public class BrailleRecognizer {

	public static void main(String[] args) throws IOException {

		LettersDataset dataset = new LettersDataset();

		List<List<Double>> in = dataset.getInputs();
		List<List<Double>> out = dataset.getOutputs();
		Trainer trainer = new MultiOutputTrainer(256, 100, 26, 0.2);
		TrainRunner runner = new TrainRunner(trainer);
		// comment this if run for the first time
		trainer.loadFromPath("/network/multioutput_braille.bin");

		System.out.println("Training starts.");
		List<Double> run = runner.run(in, out, 0.01);
		trainer.saveToPath("/network/multioutput_braille.bin");
		System.out.println("Training ended. Epochs=" + run.size());
		System.out.println("MSE-s: " + run);
		System.out.println("Now testing is performed.");

		// verification for normal inputs
		verifyInputs("normal inputs", dataset, in, out, trainer);

		// for whitenoise

		int trials=10;
		
		averageWhiteNoiseVerification(dataset, out, trainer, 0.1, trials);
		averageWhiteNoiseVerification(dataset, out, trainer, 0.2, trials);
		averageWhiteNoiseVerification(dataset, out, trainer, 0.3, trials);
		averageWhiteNoiseVerification(dataset, out, trainer, 0.4, trials);
		
		// sliced
		System.out.println("Testing inputs with vertically removed part");
		in = dataset.getVerticallyRemovedInputs();
		verifyInputs("vertically removed inputs", dataset, in, out, trainer);

		System.out.println("Testing inputs with horizontally removed part");
		in = dataset.getHorizontallyRemovedInputs();
		verifyInputs("horizontally removed inputs", dataset, in, out, trainer);

		System.out.println("TESTING ENDED.");
	}

	private static void averageWhiteNoiseVerification(LettersDataset dataset, List<List<Double>> out, Trainer trainer,
			double percentage, int trials) throws IOException {
		List<List<Double>> in;
		int errorSum=0;
		System.out.println("---- Tests for whitenoised inputs at rate "+percentage*100+"%");
		for (int i=0; i < trials; i++) {
			in = dataset.getWhiteNoisedInputs(percentage);
			errorSum+=verifyInputs(""+ percentage*100+"% whitenoise inputs, trial "+i, dataset, in, out, trainer);
		}
		System.out.println("---- Average error: "+1.*errorSum/trials);
	}

	private static int verifyInputs(String testName, LettersDataset dataset, List<List<Double>> in, List<List<Double>> out,
			Trainer trainer) {
		System.out.println("     > Testing with " + testName);
		int errors = 0;
		for (int i = 0; i < out.size(); i++) {
			String expected = dataset.valueOf(out.get(i));
			String actual = dataset.valueOf(trainer.test(in.get(i)));
			if (!actual.equals(expected)) {
				++errors;
				System.out.println("       * actual: " + actual + ", expected: " + expected);
			}
		}

		System.out.println("      > Ended with " + errors + " errors (" + Math.ceil(100.0 * errors / 52) + "%)");
		return errors;
	}

}
