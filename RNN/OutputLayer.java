public class OutputLayer extends Layer {

    Matrix true_output;

    public OutputLayer(){
        
    }

    private void initLayer(int neurons){
        this.neurons = neurons;
        this.weights = new Matrix(neurons, 1);
        this.d_bias_nodes = new Matrix(1, 1);
        this.d_bias_nodes.set(0, 0, 1);
        this.output_vector = new Matrix(1, neurons);
    }

    @Override
    public boolean isConnected(){
        return last_layer != null;
    }

    @Override
    public void connect(Layer last_layer){
        super.connect(last_layer);
        initLayer(last_layer.neurons);
    }

    @Override
    public void update(double learning_r){}



    public void setTrueOutput(Matrix t_output){
        if(t_output.rows != output_vector.rows || t_output.cols != output_vector.cols) throw new IllegalArgumentException("dimensions of output vectors do not match!");
        this.true_output = t_output;
    }


    public Matrix frontprop(){
        this.output_vector = last_layer.getOutputVector();
        this.frontproped = true;
        weights.transpose(); // critical action
        return this.output_vector;
    }

    public Matrix backprop(){ // leastsquares for now
        if(!frontproped) throw new IllegalCallerException("Layer is not frontpropagated!");
        if(true_output == null) throw new IllegalCallerException("No coorect output exists, gradient cennot be computed!");
        
        for(int i = 0; i != weights.cols; ++i){
            weights.set(0, i, -2*(true_output.at(0,i) - output_vector.at(0, i)));
        }
        frontproped = false;
        weights.transpose(); // critical action

        return weights;
    }


    
}
