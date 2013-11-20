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
		
		//Adding image distortions manually
		imageVerticalPartRemoval(toReturn);
		//addWhiteNoise(toReturn);
		
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
	
	@SuppressWarnings("unused")
	private static void addWhiteNoise(double[] toReturn) {
		for(int i=0;i<toReturn.length;i++) {
			toReturn[i]=1.-toReturn[i];
		}
		
	}
	
	@SuppressWarnings("unused")
	private static void imageVerticalPartRemoval(double[] toReturn) {
		
		int line=(toReturn.length/16)/2;
		int counter=0;
				
		for(int i=0;i<toReturn.length;i++) {
				counter+=1;
				if (counter<line) {
					toReturn[i]=toReturn[i];
				}
				else if (counter==2*line) {
					counter=0;
				}
		}
	}

}
