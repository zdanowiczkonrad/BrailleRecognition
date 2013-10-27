/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp.core;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TrainerTest {

	private Trainer trainer;
	private Network network;

	public static final List<Double> NETWORK_INPUT = newArrayList(1.0, 2.0, 3.0);
	public static final List<Double> NETWORK_FEEDFORWARD_OUTPUT = newArrayList(1.0, 0.5);
	public static final List<Double> EXPECTED_OUTPUT = newArrayList(0.5, -0.25);
	public static final List<Double> NETWORK_ERROR = newArrayList(-0.5,-0.75);
	public static final Double NETWORK_MEAN_ERROR = 0.8125;

	@Before
	public void setUp() {
		network = Mockito.mock(Network.class);
		trainer = new Trainer(network);
		when(network.feedForward(NETWORK_INPUT)).thenReturn(NETWORK_FEEDFORWARD_OUTPUT);
	}

	@Test
	public void shouldGetNetworkForTesting() {
		// given

		// when

		// then
		assertThat(trainer.getNetwork()).isEqualTo(network);
	}

	@Test
	public void shouldPerformTestForNetwork() throws Exception {
		// given

		// when
		List<Double> trainerTestOutput = trainer.test(NETWORK_INPUT);

		// then
		assertThat(trainerTestOutput).isEqualTo(NETWORK_FEEDFORWARD_OUTPUT);
	}

	@Test
	public void shouldCountErrorForSingleTest() {
		// given

		// when
		List<Double> trainerTestOutput = trainer.test(NETWORK_INPUT);
		List<Double> actualError = trainer.getError(EXPECTED_OUTPUT, trainerTestOutput);

		// then
		assertThat(actualError).isEqualTo(NETWORK_ERROR);
	}

	@Test
	public void shouldPerformBackPropagationBasedOnErrorWhenTraining() {
		// given

		// when
		trainer.train(NETWORK_INPUT, EXPECTED_OUTPUT);

		// then
		verify(network).backPropagation(NETWORK_ERROR);
	}

	@Test
	public void shouldGetNetworkError() {
		// given

		// when
		List<Double> trainerTestOutput = trainer.test(NETWORK_INPUT);
		List<Double> actualError = trainer.getError(EXPECTED_OUTPUT, trainerTestOutput);

		// then
		assertThat(trainer.getNetError(actualError)).isEqualTo(NETWORK_MEAN_ERROR);
	}
	
	
}
