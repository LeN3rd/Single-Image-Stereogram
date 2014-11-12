import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;




/* made by LeN3rd. 
 * quickly written Spagetthi code
 * Input Image needs to be the same size, as the output file.
 * TODO:
 * NOT hardcoded image paths. Im just too lazy
 */

public class stereogram{
	
	public static int height = 800;
	public static int width = 1000;
	public static int patternwidth = 100;
	
	public static void main(String [] args){
		Random randomGenerator = new Random(System.currentTimeMillis());
		
		// Fill image with random colors 
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		int [][] farbe = new int[patternwidth][height];
		for ( int i = 0; i < patternwidth; i++){
			for( int j= 0; j < height; j++){
				// ok i lied, pseudorandom, to avoid the worst of eye cancer
				int rgb = new Color((float)(randomGenerator.nextFloat()*0.2),(float)(randomGenerator.nextFloat()*0.8),(float)(randomGenerator.nextFloat()*0.8)).getRGB();

				farbe[i][j] = rgb;

			}
		}
		
		
		// put it in an image buffer
		int [][]imagebuffer = new int[width][height];
		for( int h = 0; h < (int)(width/patternwidth); h++){			
		   for ( int i = 0; i < patternwidth; i++){
			   for( int j= 0; j < height; j++){
				   imagebuffer[i+(int)(h*patternwidth)][j] = farbe[i][j];
			   }
		   }
		}
		
		
		
		File inputfile = new File("kevin.png");
		BufferedImage inputimage;
		try{
		  inputimage = ImageIO.read(inputfile);
		
		
		for( int h = 0; h < (int)(width/patternwidth); h++){
			
		   
		   for ( int i = 0; i < patternwidth; i++){
			   for( int j= 0; j < height; j++){
				   
				      
				       int argbnew = inputimage.getRGB(i+h*patternwidth, j);
				       
				       int rgb[] = new int[] {
				    		    (argbnew >> 16) & 0xff, //red
				    		    (argbnew >>  8) & 0xff, //green
				    		    (argbnew      ) & 0xff  //blue
				    		};

				       // this is where the magic happens
				       if ( h != 0 && i+(int)((h-1)*patternwidth-rgb[1]*((patternwidth-60)/255.0))<width && i+(int)((h-1)*patternwidth-rgb[1]*((patternwidth-60)/255.0)) > 0  )
				          imagebuffer[i+(int)(h*patternwidth)][j] = imagebuffer[i+(int)((h-1)*patternwidth-rgb[1]*((patternwidth-60)/255.0))][j];

					   
				   
				   image.setRGB(i+(h*patternwidth),j,imagebuffer[i+(h*patternwidth)][j]);	
			   }
		    }
		}
		
		
		File outputfile = new File("saved.png");
		try{
		ImageIO.write(image, "png",  outputfile);
		}catch(IOException e){
			System.out.println("peter");
		}
		
		}catch(IOException e ) {
		}
		
		
		
	}
}