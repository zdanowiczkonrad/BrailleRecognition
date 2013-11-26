/**
 * @author Konrad Zdanowicz (zdanowicz.konrad@gmail.com)
 * 
 */
package com.kzdanowi.studies.softcomputing.inputs;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {

	public static double[] getImage(String path) throws IOException {
		File file = readFile(path);

		BufferedImage bufferedImage = ImageIO.read(file);
		Raster raster = bufferedImage.getData();

		double[] toReturn = getPixelArray(raster);
		normalizeOutput(toReturn);
		
		return toReturn;
	}

	private static void normalizeOutput(double[] toReturn) {
		for(int i=0;i<toReturn.length;i++) {
			toReturn[i]=1.-toReturn[i]/255;
		}
	}

	private static double[] getPixelArray(Raster raster) {
		double[] toReturn = new double[getPixelCount(raster)];
		raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), toReturn);
		return toReturn;
	}

	private static int getPixelCount(Raster raster) {
		return raster.getWidth() * raster.getHeight();
	}

	private static File readFile(String path) throws FileNotFoundException {
		File file = new File(path);

		if (!file.exists()) {
			throw new FileNotFoundException(String.format("The file under the given path %s could not be found", path));
		}
		return file;
	}

	  // Letter Distortions

    public static double[] getVerticallyRemovedImage(String path)
                    throws IOException {

            File file = readFile(path);
            BufferedImage bufferedImage = ImageIO.read(file);
            Raster raster = bufferedImage.getData();
            double[] toReturn = getPixelArray(raster);
            normalizeOutput(toReturn);

            VerticalPartRemoval(toReturn);

            return toReturn;
    }

    public static double[] getHorizontallyRemovedImage(String path)
                    throws IOException {

            File file = readFile(path);
            BufferedImage bufferedImage = ImageIO.read(file);
            Raster raster = bufferedImage.getData();
            double[] toReturn = getPixelArray(raster);
            normalizeOutput(toReturn);

            HorizontalPartRemoval(toReturn);

            return toReturn;
    }

    public static double[] getWhiteNoisedImage(String path,double noiseDensity) throws IOException {

            File file = readFile(path);
            BufferedImage bufferedImage = ImageIO.read(file);
            Raster raster = bufferedImage.getData();
            double[] toReturn = getPixelArray(raster);
            normalizeOutput(toReturn);

            addWhiteNoise(toReturn,noiseDensity);

            return toReturn;
    }

 
    private static void addWhiteNoise(double[] toReturn,double noiseDensity) {
       
            for (int i = 0; i < toReturn.length; i++) {
                    if (java.lang.Math.random() <= noiseDensity) { // if the random
                                                                                                                    // value from range
                                                                                                                    // <0,1) is less
                                                                                                                    // than noise
                                                                                                                    // density, then add
                                                                                                                    // noise for pixel
                            toReturn[i] = 1. - toReturn[i]; // returns the opposite color
                    }
            }
    }

    private static void HorizontalPartRemoval(double[] toReturn) {

            for (int i = 0; i < toReturn.length; i++) {

                    if (i > toReturn.length / 2) {
                            toReturn[i] = 0;
                    }

            }
    }

    private static void VerticalPartRemoval(double[] toReturn) {

            int line = (toReturn.length / 16) / 2;
            int counter = 0;

            for (int i = 0; i < toReturn.length; i++) {
                    counter += 1;

                    if (counter > line && counter < 2 * line) {
                            toReturn[i] = 0; // after the middle of the row, put zeros
                    } else if (counter == 2 * line) {
                            counter = 0; // New line
                    }
            }
    }

	
	
	
}
