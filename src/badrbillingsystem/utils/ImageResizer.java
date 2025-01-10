
package badrbillingsystem.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;



public class ImageResizer {
//    public void resizeImage(File originalImage, File resizedImage, int width, int height , String format){
//        try {
//            BufferedImage original = ImageIO.read(originalImage);
//            BufferedImage resized = new BufferedImage(width, height, original.getType());
//            Graphics2D g2 = resized.createGraphics();
//            g2.drawImage(original, 0, 0, width, height, null);
//            g2.dispose();
//            ImageIO.write(resized, format, resizedImage);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void resizeImage(File source, File dist, String imageExtention, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(source);
            BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, width, height);
            ImageIO.write(resizedImage, imageExtention, dist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
