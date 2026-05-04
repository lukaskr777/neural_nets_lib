import java.util.Arrays;

public class Run {
    


    public static void print(Object o){
        System.out.print(o);
    }

    public static void main(String[] args){

        Network xor = new Network();

        int t = 0;
        int f = 0;
        boolean repeat = true;
        boolean reassigned = true;
        while(repeat){
            repeat = false;

            // index 0 -> true; index 1 -> true; so at index 0 it should have 0 weight and at index 1 it should have 1 weight
            if(xor.learnXor(true, true,new double[]{-1,1})){
                ++t;
            } 
            else{
                ++f;
                repeat = true;
                reassigned = false;
            }
            if(xor.learnXor(true, false,new double[]{1,-1})){
                ++t;
            }
            else{
                ++f;
                repeat = true;
                reassigned = false;
            }
            if(xor.learnXor(false, false,new double[]{-1,1})){
                ++t;
            }
            else{
                ++f;
                repeat = true;
                reassigned = false;
            }
            if(xor.learnXor(false, true, new double[]{1,-1})){
                ++t;
            }
            else{
                ++f;
                repeat = true;
                reassigned = false;
            }

            if(repeat == false && reassigned == true){
                print("[ERROR]");
            }
            
            if(f > 5000){
                print("reassign\n");
                t = 0;
                f = 0;
                xor.reassignWeights();
                reassigned  = true;
            }
            
            
        }
        print(xor.xor(true, true) + "\n");
        print(xor.xor(true, false) + "\n");
        print(xor.xor(false, false) + "\n");
        print(xor.xor(false, true) + "\n");
        print("success: " + t + "\n");
        print("fail: " + f + "\n");

        double[][] h_w = xor.hidden_weights;
        double[][] o_w = xor.output_weights;

        print("hidden weights: \n");
        for(double[] n : h_w){
            print(Arrays.toString(n) + "\n");
        }
        print("output weights: \n");
        for(double[] n: o_w){
            print(Arrays.toString(n) + "\n");
        }

        

    }
}
