package com.jornada.server.classes.image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class ImageCompressor {

	   public static void compressAndSave(String imageFolder, String imageFile, float quality) throws IOException
       {
           
		String strImageAddress = imageFolder+"/"+imageFile;
        BufferedImage image = ImageIO.read(new File(strImageAddress));
       // Get a ImageWriter for jpeg format.
        
       Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
       if (!writers.hasNext()) throw new IllegalStateException("No writers found");
       ImageWriter writer = (ImageWriter) writers.next();
       // Create the ImageWriteParam to compress the image.
       ImageWriteParam param = writer.getDefaultWriteParam();
       param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
       param.setCompressionQuality(quality);
       // The output will be a ByteArrayOutputStream (in memory)
       ByteArrayOutputStream bos = new ByteArrayOutputStream(32768);
       ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
       writer.setOutput(ios);
       writer.write(null, new IIOImage(image, null, null), param);
       ios.flush(); // otherwise the buffer size will be zero!
       // From the ByteArrayOutputStream create a RenderedImage.
       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
       RenderedImage out = ImageIO.read(in);
       int size = bos.toByteArray().length;
       
       ImageIO.write(out, "JPG", new File(imageFolder+"/"+"compressed-"+imageFile));
       System.out.println("Compressed to " + quality + ": " + size + " bytes");
       // Uncomment code below to save the compressed files.
   //    File file = new File("compressed."+quality+".jpeg");
   //    FileImageOutputStream output = new FileImageOutputStream(file);
   //    writer.setOutput(output); writer.write(null, new IIOImage(image, null,null), param);
       }
	   
	   
	     public static void Thumbnail(String imageFolder, String imageName, float quality) throws IOException{
	         ImageIcon originalIcon = new ImageIcon(imageFolder+imageName);
	                 //ImageIO.read(new File("C:/temp/foto/foto1.jpg"));
	         double scale = quality;  // 50 %
	         int w = (int) (originalIcon.getIconWidth() * scale);
	         int h = (int) (originalIcon.getIconHeight() * scale);
	         BufferedImage outImage = new BufferedImage(w, h, BufferedImage.SCALE_SMOOTH);                
	         AffineTransform trans = new AffineTransform();
	         trans.scale(scale, scale);
	         Graphics2D g = outImage.createGraphics();
	         g.drawImage(originalIcon.getImage(), trans, null);
	         g.dispose();
	         
	         ImageIO.write(outImage, "JPG", new File(imageFolder+"compressed-"+imageName));

	      }	   
	
}
