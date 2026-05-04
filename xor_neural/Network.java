import java.util.Arrays;

public class Network{



    public Network(){
        reassignWeights();
    }

    public void reassignWeights(){
        initHiddenWeights();
        initOutputWeights();
    }

    private double signum(double input){ // returns number between -1 and 1
        return 2*(1/(1 + Math.exp(-input)) - 0.5);
    }


    // each hidden node has 3 weights because the last is the BIAS NODE!!!
    private void initHiddenWeights(){
        hidden_weights = new double[2][]; // each X index is a node in hidden layer 
        for(int i = 0; i != hidden_weights.length; ++i){
            hidden_weights[i] = new double[]{randomNegate(Math.random()), randomNegate(Math.random()),randomNegate(Math.random())};
        }
    }
    private void initOutputWeights(){
        output_weights = new double[2][];
        for(int i = 0; i != output_weights.length; ++i){
            output_weights[i] = new double[]{randomNegate(Math.random()), randomNegate(Math.random()),randomNegate(Math.random())};
        }
    }



    private double randomNegate(double val){
        if(Math.random() >= 0.5){
            return -val;
        }
        return val;
    }
   
    public boolean xor(boolean left, boolean right){
        double[] input = new double[]{(left)? 1 : -1, (right) ? 1 : -1};
        this.input_layer = input;
        double[] output = getOutputValues();
        return evalOutput(output);
    }

    public boolean learnXor(boolean left, boolean right, double[] correct_output){
        double[] input = new double[]{(left)? 1 : -1, (right) ? 1 : -1};
        this.input_layer = input;
        double[] output = getOutputValues();
        //System.out.println("output " + Arrays.toString(output));
        boolean result = evalOutput(output);
        boolean correct_result = evalOutput(correct_output);

        if(result == correct_result) return true;
        else{
            adjustWeights(correct_output[0], correct_output[1]);
            return false;
        }
    }
    

    private boolean evalOutput(double[] output){
        int max_i = 0;
        double max_v = output[0];

        for(int i = 1; i != output.length; ++i){
            if(output[i] > max_v){
                max_i = i;
                max_v = output[i];
            }
        }
        return output_elements[max_i]; // get the output element that has the heighest weight
    }

    private double[] getOutputValues(){

        double[] current_input = input_layer;
        double[] current_output = new double[current_input.length];

        // hidden layer values -------------------------
        for(int i = 0; i != hidden_weights.length; ++i){
            double[] hidden_weight = hidden_weights[i];
            current_output[i] = signum(scalarMultiplication(current_input, hidden_weight));
        }
        current_input = current_output;
        current_output = new double[current_input.length];
        
        // output layer values -------------------------
        for(int i = 0; i != output_weights.length; ++i){
            double[] output_weight = output_weights[i];
            current_output[i] = signum(scalarMultiplication(current_input, output_weight)); 
        }
        return current_output;
    }
    private double scalarMultiplication(double[] input_vector, double[] weight_vector){
        double sum = 0;
        for(int i = 0; i != input_vector.length; ++i){
            sum += input_vector[i]*weight_vector[i];
        }
        sum += weight_vector[weight_vector.length-1]; // last element is the bias node!!!
        return sum;
    }

    private void adjustWeights(double out1, double out2){

        double wk1=  hidden_weights[0][0];
        double wk2=  hidden_weights[0][1];
        double wkb=  hidden_weights[0][2];

        double wp1 = hidden_weights[1][0];
        double wp2 = hidden_weights[1][1];
        double wpb = hidden_weights[1][2];

        double wo1 = output_weights[0][0];
        double wo2 = output_weights[0][1];
        double wob = output_weights[0][2];

        double ws1 = output_weights[1][0];
        double ws2 = output_weights[1][1];
        double wsb = output_weights[1][2];

        double[] w = {wk1,wk2,wkb,wp1,wp2,wpb,wo1,wo2,wob,ws1,ws2,wsb};
        double[] slopes = new double [w.length];

        for(int i = 0; i != w.length; ++i){
            w[i] += DIFF_C;
            double high_y = netFunction(w, out1, out2);
            w[i] -= 2*DIFF_C;
            double low_y = netFunction(w, out1, out2);
            w[i] += DIFF_C;

            slopes[i] = (high_y-low_y)/(2*DIFF_C);

            w[i] -= signum(slopes[i]*LEARNING_C);
        }
        //System.out.println("slopes: " + Arrays.toString(slopes));

        for(int i = 0;  i != 2; ++i){
            for(int j = 0; j != 3; ++j){
                hidden_weights[i][j] = w[i*3+j]; // if slope is negative, then size up the value, else size down
            }
        }

        for(int i = 0;  i != 2; ++i){
            for(int j = 0; j != 3; ++j){
                output_weights[i][j] = w[6+ i*3+j];
            }
        }
        
    }

    

    private double netFunction(double[] weights, double out1, double out2){
        
        double wk1=  weights[0];
        double wk2=  weights[1];
        double wkb=  weights[2];

        double wp1 = weights[3];
        double wp2 = weights[4];
        double wpb = weights[5];

        double wo1 = weights[6];
        double wo2 = weights[7];
        double wob = weights[8];

        double ws1 = weights[9];
        double ws2 = weights[10];
        double wsb = weights[11];
        
        
        double h1=  input_layer[0]*wk1 + input_layer[1]*wk2 + wkb;
        double h2 = input_layer[0]*wp1 + input_layer[1]*wp2 + wpb;

        double hh1 = h1*wo1 + h2*wo2 + wob;
        double hh2 = h1*ws1 + h2*ws2 + wsb;

        return (Math.pow(hh1 - out1,2) + Math.pow(hh2 - out2,2));
    }



    double[] input_layer;
    double[][] hidden_weights;
    
    double[][] output_weights;
    boolean[] output_elements = new boolean[]{true,false};

    private final double LEARNING_C = 0.0001;
    private final double DIFF_C = 0.001;
}