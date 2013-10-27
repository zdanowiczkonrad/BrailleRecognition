/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class TrainerRunnerTest {

	private TrainRunner runner;
	private Trainer trainerMock;
	private List<List<Double>> inputs;
	private List<List<Double>> outputs;

	@Before
	public void setUp() {
		trainerMock = mock(Trainer.class);
		runner = new TrainRunner(trainerMock);

		inputs = new ArrayList<List<Double>>();
		inputs.add(newArrayList(1.0, 1.0));
		inputs.add(newArrayList(2.0, 2.0));

		outputs = new ArrayList<List<Double>>();
		outputs.add(newArrayList(1.0));
		outputs.add(newArrayList(2.0));
	}

	@Test
	public void shouldCountMeanSquareForSinglePass() {
		// given
		when(trainerMock.getNetError(anyList())).thenReturn(1.0).thenReturn(2.0);

		// when
		Double mse = runner.singleRunError(inputs, outputs);

		// then
		verify(trainerMock, times(inputs.size())).getNetError(anyList());
		verify(trainerMock,times(inputs.size())).train(anyList(), anyList());
		assertThat(mse).isEqualTo(3.0);
	}

	@Test
	public void shouldPerformTrainingUntilDeltaReached() {
		// given
		when(trainerMock.getNetError(anyList())).thenReturn(0.5).thenReturn(0.5).thenReturn(0.4).thenReturn(0.4).thenReturn(0.2).thenReturn(0.3).thenReturn(0.2).thenReturn(0.1);
		
		// when
		List<Double> mses=runner.run(inputs,outputs,0.5);
		
		// then
		assertThat(mses).hasSize(3);
		assertThat(mses).isEqualTo(newArrayList(1.0,0.8,0.5));
	}
	
}
