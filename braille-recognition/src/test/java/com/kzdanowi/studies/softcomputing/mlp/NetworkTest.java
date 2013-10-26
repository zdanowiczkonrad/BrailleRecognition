/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.fest.assertions.Delta;
import org.junit.Test;

import com.google.common.collect.Lists;

public class NetworkTest {

	@Test
	public void shouldCreateNetworkWithNoLayer() {
		// given, when
		Network network = new Network();

		// then
		assertThat(network.getLayers()).hasSize(0);
	}

	@Test
	public void shouldContainAddedLayer() {
		// given
		Network network = new Network();
		Layer layer = new Layer(0, 0, null, null);

		// when
		network.addLayer(layer);

		// then
		assertThat(network.getLayers()).hasSize(1);
		assertThat(network.getLayers()).contains(layer);
	}

	@Test
	public void shouldPerformFeedForwardOnAllLayers() throws InputOrWeightSizeException {
		/**
		 * This test covers the first input from example at
		 * http://www.nnwj.de/backpropagation.html
		 */
		// given
		Network network = createTwoLayerNetwork();

		// when

		List<Double> input = newArrayList(0.0, 1.0);
		List<Double> expected = newArrayList(0.643962658);

		// then
		assertDoubleArraysEqualWithDelta(network.feedForward(input), expected, Delta.delta(0.000000001));
	}
	
	@Test
	public void shouldPerformBackPropagationOnAllLayers() throws InputOrWeightSizeException {
		// given
		Network network = createTwoLayerNetwork();
		Double error=-0.643962658;

		// when
		List<Double> input = newArrayList(0.0, 1.0);
		network.feedForward(input);
		network.backPropagation(error);
		
		// then
		List<Double> expectedWeights=Lists.newArrayList(0.326593362,0.793109407,0.62,0.512648936,0.42,-0.209958271);
		assertNetworkLayerHasFollowingWeights(network,expectedWeights);
	}

	private void assertNetworkLayerHasFollowingWeights(Network network, List<Double> expectedWeights) {
		int i=0;
		for(int layerIndex=network.getLayers().size()-1;layerIndex>=0;layerIndex--)
		{
			Layer layer=network.getLayers().get(layerIndex);
			for (Perceptron perceptron : layer.getPerceptrons()) {
				for(Double weight : perceptron.getWeights())
				{
					Double expected = expectedWeights.get(i++);
					System.out.println("Expected: "+expected+", actual: "+weight);
					assertThat(expected).isEqualTo(weight,Delta.delta(0.000000001));
				}
			}
		}
	}

	private Network createTwoLayerNetwork() {
		Network network = new Network();

		Random randomGenerator = mock(Random.class);
		when(randomGenerator.nextDouble()).thenReturn(0.62).thenReturn(0.55).thenReturn(0.42).thenReturn(-0.17).thenReturn(0.35)
				.thenReturn(0.81);
		ActivationFunction activationFunction = new SigmoidActivationFunction();
		Layer hiddenLayer = new Layer(2, 2, randomGenerator, activationFunction);
		Layer outputLayer = new Layer(1, 2, randomGenerator, activationFunction);
		network.addLayer(hiddenLayer);
		network.addLayer(outputLayer);
		return network;
	}

	private void assertDoubleArraysEqualWithDelta(List<Double> actual, List<Double> expected, Delta delta) {
		if (actual.size() != expected.size()) {
			fail("Actual has different size than expected");
		}
		for (int i = 0; i < actual.size(); i++) {
			assertThat(actual.get(i)).isEqualTo(expected.get(i), delta);
		}
	}

}
