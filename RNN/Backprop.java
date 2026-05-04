import java.util.Arrays;

public class Backprop {


    public static void leastSquaresProp(Dense[] layers, Matrix true_output){
        Matrix output_der = squaresOutputDerivate(layers[layers.length-1].getOutputVector(), true_output);

        for(int l = layers.length -1; l != 0; --l){
            
            Matrix weights = layers[l].weights;
            Matrix bias_nodes = layers[l].bias_nodes;

            Matrix d_weights = layers[l].d_weights;
            Matrix d_biasnodes = layers[l].d_bias_nodes;

            for(int r = 0; r != weights.rows; ++r){
                for(int c = 0; c != weights.cols; ++c){
                    
                }
            }
        }
    }

    // we have to add the bias nodes
    public static Matrix[] leastSquaresProp(Matrix[] weights, Matrix[] results, Matrix true_output){
        Matrix output_der = squaresOutputDerivate(results[results.length-1], true_output);

        Matrix[] derivs = new Matrix[weights.length+1];
        derivs[derivs.length-1] = output_der;

        for(int i = weights.length-1; i >= 0; --i){
            Matrix now_weight_deriv = weightDerivative(results[i], weights[i], derivs[i+1]); 
            derivs[i] = now_weight_deriv;
        }
        return derivs;

    }

    // for the least squares function
    private static Matrix squaresOutputDerivate(Matrix output, Matrix true_output){
        Matrix r = new Matrix(output.cols, 1);
        for(int i = 0; i != r.rows; ++i){
            r.set(i,0,-2*(true_output.at(0, i) - output.at(0, i)));
        }
        return r;
    }



    // each row in derivative matrix is dot producted by each value (as vector) in the result vector row, thus getting the correct derivative
    private static Matrix weightDerivative(Matrix current_result, Matrix current_weights, Matrix last_deriv){
        Matrix deriv = new Matrix(current_weights.rows, current_weights.cols);

        for(int i = 0; i != last_deriv.rows; ++i){
            double[] deriv_row = last_deriv.getRow(i);

            for(int j = 0; j != current_result.cols; ++j){
                double[] val_row = new double[deriv_row.length];
                Arrays.fill(val_row,current_result.at(0,j));

                deriv.set(j, i, Matrix.dot(deriv_row, val_row));
            }
        }
        return deriv;
    }
    
}
