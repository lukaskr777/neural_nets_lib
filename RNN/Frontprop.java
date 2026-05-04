public class Frontprop {


    // first weight matrix is the first layer of weights
    public static Matrix[] prop(Matrix[] weights, Matrix input){
        Matrix[] output = new Matrix[weights.length + 1];
        output[0] = input.clone();

        for(int i = 0; i != weights.length; ++i){
            output[i+1] = Matrix.multiply(output[i],weights[i]);
        }
        return output;

    }

    public static void prop(Layer[] layers){ // propagate the outputs
        for(int i = 1; i != layers.length; ++i){
            layers[i].frontprop();
        }

    }
    
}
