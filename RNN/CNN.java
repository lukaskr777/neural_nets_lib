public class CNN {


    private Layer[] layers;
    private Matrix input, output;
    private boolean backpropped = false;


    @Override
    public String toString(){
        String s="";

        int i = 0;
        for(Layer l : layers){
            s+= "layer: " + (i++) + "\n";

            s+= "------------- weights: \n";
            if(l.weights != null){
                s += l.weights.toString();
            }
            s+= "------------- bias: \n";
            if(l.bias_nodes != null){
                s += l.bias_nodes.toString();
            }
            s+= "==================\n";
            s+= "-------------weight der: \n";
            if(l.d_weights != null){
                s+= l.d_weights.toString();
            }
            s+= "----------------bias der: \n";
            if(l.d_bias_nodes != null){
                s+= l.d_bias_nodes.toString();
            }
            s+= "--------------output vector:\n";
            if(l.output_vector != null){
                s+= l.output_vector.toString();
            }
            s+= "|\n";
        }
        return s;
    }

    public CNN(Layer[] layers, double learning_r){
        if(layers.length < 3) throw new IllegalArgumentException("There has to be at least 3 layers - input, hidden, output layer!");
        this.layers = layers;
        this.LEARNING_R = learning_r;
        connectLayers();
    }

    private void connectLayers(){
        for(int i = 1; i != layers.length; ++i){
            layers[i].connect(layers[i-1]);
        }
    }

    public void learnData(Matrix input, Matrix output){
        if(input.cols != layers[0].neurons) throw new IllegalArgumentException("Input neuron size is different than your input");
        if(output.cols != layers[layers.length-1].neurons) throw new IllegalArgumentException("Output neuron size is different than your output!");

        ((InputLayer)layers[0]).setInput(input);
        ((OutputLayer)layers[layers.length-1]).setTrueOutput(output);

        frontprop();
        backprop();
        update();
    }

    public Matrix classify(Matrix input){
        if(input.cols != layers[0].neurons) throw new IllegalArgumentException("Input neuron size is different than your input");

        ((InputLayer)layers[0]).setInput(input);
        frontprop();

        return layers[layers.length-1].getOutputVector();
    }

    // front prpopagate result of input
    private void frontprop(){
        for(int i = 1; i != layers.length; ++i){
            layers[i].frontprop();
        }
        backpropped = false;
    }

    // backpropagate gradient to each variable (weight, bias)
    private void backprop(){
        for(int i = layers.length-1; i != 0; --i){
            layers[i].backprop();
        }
        backpropped = true;
    }

    // update 
    private void update(){
        if(!backpropped) throw new IllegalCallerException("Error gradient not propagated!");

        for(int i = 1; i != layers.length; ++i){
            layers[i].update(LEARNING_R);
        }
    }   


    public double LEARNING_R = 0.1;
    
}
