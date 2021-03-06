/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.mlp;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.kzdanowi.studies.softcomputing.mlp.core.WeightGenerator;

/**
 * Test for default weight random generator
 */
public class DefaultWeightGeneratorTest {

	@Test
	public void returnOutputsShouldBeInDefaultRange() {
		// given
		WeightGenerator generator = new DefaultWeightGenerator();

		// when

		// then
		for (int i = 0; i < 100000; i++) {
			assertThat(generator.next()).isGreaterThanOrEqualTo(-1.).isLessThanOrEqualTo(1);
		}
	}
	@Test
	public void returnOutputsShouldBeInAppropriateRange() {
		// given
		WeightGenerator generator = new DefaultWeightGenerator(-2.42,5.50);

		// when

		// then
		for (int i = 0; i < 100000; i++) {
			assertThat(generator.next()).isGreaterThanOrEqualTo(-2.42).isLessThanOrEqualTo(5.50);
		}
	}
}
