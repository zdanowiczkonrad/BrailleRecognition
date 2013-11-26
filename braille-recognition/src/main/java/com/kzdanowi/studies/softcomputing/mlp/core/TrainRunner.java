/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

/**
 * Train runner
 */
public class TrainRunner {

	public Trainer trainer;
	public int sysoutInterval=0;
	private int howOftenSysout=100;
	
	public TrainRunner(Trainer trainer) {
		this.trainer = trainer;

	}

	public Double singleRunError(List<List<Double>> inputs, List<List<Double>> outputs) {
		checkState(inputs.size() == outputs.size(), "Input data has different length than output data!");

		Double netError = 0.0;
		sysoutInterval++;
		
		for (int i = 0; i < inputs.size(); i++) {

			List<Double> input = inputs.get(i);
			List<Double> expected = outputs.get(i);

			trainer.train(input, expected);

			List<Double> actual = trainer.test(input);
			
			if(sysoutInterval%howOftenSysout==0)
			{
				System.out.println(String.format("Expected: %s, actual: %s,",expected,actual));
			}
			
			netError += trainer.getNetError(trainer.getError(expected, actual));
		}
		
		if(sysoutInterval%howOftenSysout==0)
		{
			System.out.println(" *    MSE: "+netError);
			String path = "/networks/net_Braille_snapshot.bin";
			GenericMultiLayerNetwork.serializeNetwork(trainer.getNetwork(), path);

		}
	
		return netError;
	}

	public List<Double> run(List<List<Double>> inputs, List<List<Double>> outputs, Double delta) {

		List<Double> netErrors = new ArrayList<Double>();

		Double error = singleRunError(inputs, outputs);
		netErrors.add(error);

		while (error > delta) {
			netErrors.add(error = singleRunError(inputs, outputs));
		}

		return netErrors;
	}

}
