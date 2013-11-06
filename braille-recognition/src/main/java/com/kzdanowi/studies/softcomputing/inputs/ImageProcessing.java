/**
 * @author Efthymios Doukas (efdouk22@gmail.com)
 * 
 */

package com.kzdanowi.studies.softcomputing.inputs;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessing
{
    private static String dir="src/main/java/com/kzdanowi/studies/softcomputing/inputs/data"; // add here the directory to the folder contains the image
    public int [][] readImage(String filename) throws IOException
    {
        File file = new File(dir , filename);// file object to get the file, the second argument is the name of the image file
        BufferedImage image = ImageIO.read(file);
        Raster image_raster = image.getData();
       
        int[][] original; // where we'll put the image
               
        //get pixel by pixel
        int[] pixel = new int[1];
        int[] buffer = new int[1];
       
        // declaring the size of arrays
        original = new int[image_raster.getWidth()][image_raster.getHeight()];

       
        //get the image in the array
        for(int i = 0 ; i < image_raster.getWidth() ; i++)
            for(int j = 0 ; j < image_raster.getHeight() ; j++)
            {
                pixel = image_raster.getPixel(i, j, buffer);
                original[i][j] = pixel[0];
            }
        return original;                   
    }
    
    public static void main(String[] args) throws IOException {
    	
     	// Testing the 2D image array
    	
    	ImageProcessing letter = new ImageProcessing();
  
    	int[][] letterMatrix;
    	letterMatrix= letter.readImage("I2.jpg");
    	System.out.println("INPUT:");
		for (int i=0; i < 16; i++) {
			for (int j=0; j < 16; j++) {
    			System.out.print(letterMatrix[i][j]+" ");
    		}
    		System.out.println();
    	}
    	
    	
    }
   
}