public class Convolutional {


    public Convolutional(int[] layer_sizes){
        if(layer_sizes.length < 2) throw new IllegalArgumentException("[ERROR] There must be at least two layers!");

        this.layers = new double[layer_sizes.length-1][][]; // if we have two layers only one layer has weights, thus one matrix
        this.input_vector_size = layer_sizes[0];
        this.output_vector_size = layer_sizes[layer_sizes.length-1];

        for(int i = 1; i != layer_sizes.length; ++i){
            double[][] layer_matrix = new double[layer_sizes[i]][];


            for(int j = 0; j != layer_sizes[i]; ++j){
                layer_matrix[j] = randomRow(layer_sizes[i-1] + 1); // + 1 for the bias node 
            }

            this.layers[i-1] = layer_matrix; 
        }
    }
    public void reweight(){
        for(int l = 0; l != layers.length; ++l){
            for(int r = 0; r != layers[l].length; ++r){
                for(int w = 0; w != layers[l][r].length; ++w){
                    layers[l][r][w] = getRandom();
                }
            }
        }
    }


    public boolean train(double[] input_vector, double[] correct_output_vector){
        if(input_vector.length != input_vector_size) throw new IllegalArgumentException("[ERROR] Input vector has to be of size: " + input_vector_size);
        if(correct_output_vector.length != output_vector_size) throw new IllegalArgumentException("[ERROR] Output vector has to be of size: " + output_vector_size);

        double[] output_vector = getOutputVector(input_vector);
        if(maxIndex(output_vector) != maxIndex(correct_output_vector)){ // incorrect classification, adjustments to weights must be made
           // System.out.println("Incorrect classification");
            gradientDescent(input_vector, correct_output_vector);
            return false;
        }
        else{
            return true;
            //System.out.println("Correct classification");
        }
    }
    public double[] getOutput(double[] input_vector){
        if(input_vector.length != input_vector_size) throw new IllegalArgumentException("[ERROR] Input vector has to be of size: " + input_vector_size);

        return getOutputVector(input_vector);
    }

    private double signum(double val){
        return 2*(1/(1 + Math.exp(-val)) - 0.5);
    }

    private int maxIndex(double[] array){
        double max = array[0];
        int max_index = 0;
        for(int i = 1; i != array.length; ++i){
            if(array[i] > max){
                max = array[i];
                max_index = i;
            }
        }
        return max_index;
    }


    private void gradientDescent(double[] input_vector, double[] correct_output_vector){

        for(int layer = 0; layer != layers.length; ++layer){
            for(int row = 0; row != layers[layer].length; ++row){
                for(int weight = 0; weight != layers[layer][row].length; ++weight){
                    double slope = derivative(layer, row, weight, input_vector, correct_output_vector);
                    layers[layer][row][weight] -= (LEARNING_CONSTANT*slope); // if slope is negative we have to add to the weight, else substract
                }
            }
        }
    }

    private double derivative(int layer, int row, int weight, double[] input_vector, double[] correct_output_vector){

        layers[layer][row][weight] += VARIABLE_DIFF;
        double[] high_output_vector = getOutputVector(input_vector);
        layers[layer][row][weight] -= 2*VARIABLE_DIFF;
        double[] low_output_vector = getOutputVector(input_vector);
        layers[layer][row][weight] += VARIABLE_DIFF;

        return computeDerivative(high_output_vector, low_output_vector, correct_output_vector);
    }

    private double computeDerivative(double[] high_o, double[] low_o, double[] correct_o){
        return (errorFunction(high_o, correct_o) - errorFunction(low_o, correct_o))/(2*VARIABLE_DIFF);
    }

    private double errorFunction(double[] o, double[] correct_o){
        double y = 0;
        for(int i = 0; i != o.length; ++i){
            y += Math.pow(o[i] - correct_o[i],2);
        }
        return y;
    }

    private double[] getOutputVector(double[] input){

        double[] input_vector = input;

        for(int l = 0; l != layers.length; ++l){

            input_vector = matrixMult(layers[l], input_vector); // each layer besidest the inpit layer has weights matrix
        }
        return input_vector; // return the output vector, which can correspond to particular items... etc
    }

    private double[] matrixMult(double[][] matrix, double[] vector){ // matrix without boias node

        double[] result_vector = new double[matrix.length];

        for(int i = 0; i != matrix.length; ++i){
            result_vector[i] = signum(scalarBiasMult(matrix[i], vector)); // matrix row has also a bias node !!!
        }
        return result_vector;
        
    }

    private double scalarBiasMult(double[] vector_with_bias, double[] vector_no_bias){
        if(vector_with_bias.length - 1  != vector_no_bias.length) throw new IllegalArgumentException("[ERROR] vectors are of incompatible size!");

        double r = 0;

        for(int i = 0; i != vector_no_bias.length; ++i){
            r += vector_with_bias[i]*vector_no_bias[i];
        }
        r += vector_with_bias[vector_with_bias.length-1]; // add the bias node
        return r;
    }

    private double[] randomRow(int size){
        double[] row = new double[size];
        for(int i = 0; i != size; ++i){
            row[i] = getRandom();
        }
        return row;
    }

    private double getRandom(){
        double r = Math.random();

        if(Math.random() >= 0.5){
            return r;
        }
        return -r;
    }

    private double[][][] layers;
    private int input_vector_size;
    private int output_vector_size;
    private final double LEARNING_CONSTANT = 0.1; 
    private final double VARIABLE_DIFF = 0.1; 
    
}
