import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;



public class SampleDatabase {

    public static boolean initDB(String img_src, String label_src, String img_out, int db_size){

        FileInputStream image_in, label_in;

        int[] each_number_cnt = new int[10];
        
        try{
            image_in = new FileInputStream(img_src);
            label_in = new FileInputStream(label_src);

            int img_magic_number = (image_in.read() << 24 | image_in.read() << 16 | image_in.read() << 8 | image_in.read());
            int number_of_images = (image_in.read() << 24 | image_in.read() << 16 | image_in.read() << 8 | image_in.read());
            int img_rows = (image_in.read() << 24 | image_in.read() << 16 | image_in.read() << 8 | image_in.read());
            int img_columns = (image_in.read() << 24 | image_in.read() << 16 | image_in.read() << 8 | image_in.read());

            int label_magic_number = (label_in.read() << 24 | label_in.read() << 16 | label_in.read() << 8 | label_in.read());
            int number_of_labels = (label_in.read() << 24 | label_in.read() << 16 | label_in.read() << 8 | label_in.read());

            BufferedImage image = new BufferedImage(img_rows, img_columns, BufferedImage.TYPE_INT_ARGB);
            int[] pixels = new int[img_rows*img_columns];



            for(int i = 0 ; i < db_size; ++i){

                for(int x = 0; x != pixels.length; ++x){
                    int gray = 255 - image_in.read();
                    pixels[x] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                }

                image.setRGB(0, 0, img_columns, img_rows, pixels, 0, img_columns);
  
                int label = label_in.read();

                each_number_cnt[label]++;
                File outputfile = new File(img_out +"_"+ label + "_" + each_number_cnt[label] + ".png");

                ImageIO.write(image, "png", outputfile);

            }
            return true;


        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    
}
