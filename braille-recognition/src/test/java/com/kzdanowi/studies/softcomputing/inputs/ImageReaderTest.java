/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.inputs;

import static org.fest.assertions.Assertions.assertThat;

import java.io.FileNotFoundException;

import org.fest.assertions.Condition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ImageReaderTest {

	private static final String VALID_PATH = "src/main/java/com/kzdanowi/studies/softcomputing/inputs/data/A.jpg";

	private static final String INVALID_PATH = "invalidPath";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldReturnExceptionWhenFileNotFound() throws Exception {
		// expect
		expectedException.expect(FileNotFoundException.class);
		expectedException.expectMessage(INVALID_PATH + " could not be found");

		// when
		ImageReader.getImage(INVALID_PATH);
	}

	@Test
	public void shouldReadFileToDoubleArray() throws Exception {
		// when
		double[] image = ImageReader.getImage(VALID_PATH);

		// then
		assertThat(image).isNotNull();
	}

	@Test
	public void shouldReturnNormalizedImageArray() throws Exception {
		/**
		 * Expected is an image array with floating point values in a range
		 * <0,1>
		 */
		// given
		Condition<Double> inRange = new InRangeCondition(0., 1.);

		// when
		double[] image = ImageReader.getImage(VALID_PATH);

		// then
		assertThat(image).hasSize(16 * 16);

		for (Double eachPixel : image) {
			assertThat(eachPixel).is(inRange);
		}
	}
}
