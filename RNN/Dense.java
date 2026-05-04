public class Dense extends Layer {

    SFunction squash_function;

    public Dense(int neurons, String squash_function){
        this.neurons = neurons;
        initSFunction(squash_function);
        initBias(neurons);
    }

    @Override
    public void update(double learning_r){
        for(int c = 0; c != weights.cols; ++c){
            for(int r = 0; r != weights.rows; ++r){
                weights.set(r, c, weights.at(r, c) - learning_r*d_weights.at(r, c));
            }
            bias_nodes.set(0,c,bias_nodes.at(0, c) - learning_r*d_bias_nodes.at(0, c));
        }
    }

    private void initBias(int neurons){
        bias_nodes = new Matrix(1, neurons);
        d_bias_nodes = new Matrix(1, neurons);

        for(int i  =0; i != neurons; ++i){
            bias_nodes.set(0, i, Math.random());
        }
    }

    private void initSFunction(String squash_function){
        squash_function = squash_function.toLowerCase();
        
        if(squash_function.equals("relu")){
            this.squash_function = new Relu();
        }
        else if(squash_function.equals("step")){
            this.squash_function = new Step();
        }
        else if(squash_function.equals("signum")){
            this.squash_function = new Signum();
        }
        else if(squash_function.equals("tanh")){
            this.squash_function = new Tanh();
        }
        else if(squash_function.startsWith("iden")){
            this.squash_function = new Identity();
        }
    }

    @Override
    public void connect(Layer last_layer){
        super.connect(last_layer);

        this.weights = new Matrix(last_layer.neurons, this.neurons);
        this.d_weights = new Matrix(last_layer.neurons, this.neurons);

        randomInitWeights();
    }

    public Matrix frontprop(){
        Matrix last_output = last_layer.getOutputVector();
        output_vector = Matrix.multiply(last_output, weights);

        // add bias
        for(int b = 0; b != bias_nodes.cols; ++b){ 
            output_vector.set(0, b, output_vector.at(0, b) + bias_nodes.at(0, b));
        }

        // squash
        for(int i = 0; i != output_vector.cols; ++i){
            output_vector.set(0, i, squash_function.y(output_vector.at(0, i)));
        }

        frontproped= true;
        return output_vector;
    }

    public Matrix backprop(){
        if(!frontproped) throw new IllegalCallerException("Layer is not frontpropagated!");


        for(int c =  0; c != d_weights.cols; ++c){
            double squash_grad = squash_function.dy(output_vector.at(0, c));

            double sum = 0; // sum of next layer gradients that depend on this weight
            for(int n_c = 0; n_c != next_layer.weights.cols; ++n_c){
                sum += next_layer.d_bias_nodes.at(0, n_c)*next_layer.weights.at(c,n_c); // corresponding weights
            }
            sum*= squash_grad;

            for(int r = 0; r != d_weights.rows; ++r){ // each loop is is one neuron
                d_weights.set(r, c, sum*last_layer.output_vector.at(0,r));
            }
            d_bias_nodes.set(0, c, sum);
        }

        frontproped = false;
        return d_weights;
    }

    private void randomInitWeights(){
        int i = this.weights.cols;
        int j = this.weights.rows;

        for(int x = 0; x != i; ++x){
            for(int y = 0; y !=j; ++y){
                this.weights.set(y, x, Math.random());
            }
        }
    }
}
