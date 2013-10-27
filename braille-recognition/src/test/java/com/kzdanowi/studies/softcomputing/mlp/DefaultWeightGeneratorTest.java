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
	public void returnOutputsShouldBeInAppropriateRange() {
		// given
		WeightGenerator generator = new DefaultWeightGenerator();

		// when

		// then
		for (int i = 0; i < 100000; i++) {
			assertThat(generator.next()).isGreaterThanOrEqualTo(-5.).isLessThanOrEqualTo(5);
		}
	}

}
