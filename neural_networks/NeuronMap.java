import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class NeuronMap {

    public NeuronMap(){
        number_layer = new DescendantNeuron[10];
    }

    // first time initialising the relation values to imput_layer neurons
    public void initNumberLayerRelations(String src_dir){
        try{
            for(int x = 0; x != 10; ++x){
                String src = src_dir+x+"_init.png";
                BufferedImage img = ImageIO.read(new File(src)); 
                double[] pixels = new double[img.getWidth()*img.getHeight()];
                fillImgArray(pixels, img);

                fillNumberNeuron(pixels, x);   
                
                //printNeuron(x);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void fillImgArray(double[] arr, BufferedImage img){
        int row_size = img.getHeight();
        int col_size =  img.getWidth();
        for(int x = 0; x != row_size; ++x){
            for(int y = 0; y != col_size; ++y){
                arr[x*col_size + y] = integerToDoubleRelation(img.getRGB(y, x));

            }
        }
    }



    // initialize relation values to input layer neurons from already build database
    public void initNumberLayerDatabase(String src_dir){
        
        for(int x = 0; x != 10; ++x){
            String src = src_dir+"n_"+x+".txt";
            double[] pixels = new double[28*28];
            fillDatabaseArray(pixels, src);

            fillNumberNeuron(pixels, x);
        }
    }

    // fill up relation values of particular number neuron from database
    private void fillDatabaseArray(double[] pixels, String src){

        try{
            Scanner scanner = new Scanner(new File(src));
            for(int x = 0; x != pixels.length; ++x){
                pixels[x] = Double.parseDouble(scanner.next());
            }
            scanner.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void fillNumberNeuron(double[] pixels,int number){
        DescendantNeuron neuron = new DescendantNeuron(pixels.length, (byte)number); 
        number_layer[number] = neuron;

        for(int x = 0; x != pixels.length; ++x){
            neuron.initRelation(x, pixels[x]);
        }

    } 




    // save my state of relations to 'database' so we can load them later (we dont lose out learning gain)
    public void saveNumberLayerDatabase(String src_dir){
        for(int x = 0; x != 10; ++x){
            String src = src_dir+"n_"+x+".txt";

            double[] pixels = number_layer[x].getRelations();
            saveDatabaseArray(pixels, src);
        }
        return;
    }

    // write .txt file of relations for every number neuron
    private void saveDatabaseArray(double[] pixels,String src){
        try{
            PrintWriter writer= new PrintWriter(src,"UTF-8");
            for(int x = 0; x != pixels.length; ++x){
                
                if(x+1 % 28 == 0){
                    writer.write(String.valueOf(pixels[x])+"\n");
                }
                else{
                    writer.write(String.valueOf(pixels[x])+" "); 
                }
            }
            writer.close();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    // convert default RGB int value to double value of input layer
    private double integerToDoubleRelation(int val){
        if(val == -1) return -0.1; // if it sould be white, it will substract from the validity on a given picture
        return (double)Math.round((-val / 16800000.0)*10000)/10000;
    }


    // convert default RGB int value to double relation value of number layer
    private double integerToDoubleValue(int val){
        if(val == -1) return 0; // if it sould be white, it has no value
        return (double)Math.round((-val / 16800000.0)*10000)/10000;
    }

    private double roundValue(double val){
        return (double)Math.round(val*10000)/10000;
    }

    public void printNeuron(int x){
        System.out.println("Neuron number: "+x);
        double[] r = number_layer[x].getRelations();
        int row = 1;
        for(double b : r){
            if(row++ % 28 == 0){
                System.out.println();
            }
            System.out.print(roundValue(b)+" ");
            
        }
        System.out.println();
    }

    public boolean recognizeAndLearnImage(String src_img, int correct_number){
        recognizeImage(src_img);
        int result_number = getResult();
      
        if(result_number != correct_number){
            weakenConnection(result_number);
        }
        strenghtenConnection(correct_number);
      
        return result_number == correct_number;
    }

    private void strenghtenConnection(int number_in_layer){

        int x = 0;
        DescendantNeuron neuron_to_strengthen = number_layer[number_in_layer];
        for(TopNeuron in : input_layer){
            double error_change = getError(in, neuron_to_strengthen, x);
            neuron_to_strengthen.changeRelation(x++, Math.abs(error_change)*error_change);
        }
    }
    private void weakenConnection(int number_in_layer){

        int x = 0;
        DescendantNeuron neuron_to_weaken = number_layer[number_in_layer];
        for(TopNeuron in : input_layer){
            if(in.getValue() > 0 && neuron_to_weaken.getRelationValue(x) > 0){
                double error_change = getError(in, neuron_to_weaken, x);
                neuron_to_weaken.changeRelation(x, -(1 - Math.abs(error_change))*0.65);
            }
            ++x;
        }
    }

    private double getError(TopNeuron a, DescendantNeuron b, int b_index){
        return (a.getValue() - b.getRelationValue(b_index));
    }

    // input image of a number to recognition
    public void recognizeImage(String src_img){
        try{

            BufferedImage img = ImageIO.read(new File(src_img)); 
            int row_size = img.getHeight();
            int col_size = img.getWidth();
            
            double[] arr = new double[row_size*col_size];
            for(int x = 0; x != row_size; ++x){
                for(int y = 0; y != col_size; ++y){
                    arr[x*col_size + y] = integerToDoubleValue(img.getRGB(y, x));
                    
                }
            }
            initInputNeurons(arr);

            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void initInputNeurons(double[] toplevel_values){
        input_layer = new TopNeuron[toplevel_values.length];
        for(int x = 0; x != toplevel_values.length; ++x){
            input_layer[x] = new TopNeuron(toplevel_values[x]);
        }
    }


    // get what number is recognised of the image inputted
    public int getResult(){
        if(input_layer == null) return -1;

        for(int x = 0;  x != number_layer.length; ++x){
            number_layer[x].initValue(input_layer);
        }
        DescendantNeuron n = getMaxNeuron(); 
        System.out.println("Number: " + n.getNumber() +" by value: "+n.getValue());
        return n.getNumber();
    }


    // find number neuron with the best match
    private DescendantNeuron getMaxNeuron(){
        DescendantNeuron best_n = null;
        for(DescendantNeuron n : number_layer){
            if(best_n == null) best_n = n;
            if(best_n.getValue() < n.getValue()) best_n = n;
        }
        return best_n;
    }



    TopNeuron[] input_layer;
    DescendantNeuron[] number_layer;
    
}
