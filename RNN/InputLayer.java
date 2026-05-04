public class InputLayer extends Layer {
    

    public InputLayer(int neurons){
        this.neurons = neurons;
    }

    public void setInput(Matrix in){
        this.output_vector = in;
    }

    @Override
    public boolean isConnected(){
        return next_layer != null;
    }

    public Matrix frontprop(){
        return this.output_vector;
    }

    public Matrix backprop(){
        return null;
    }

    public void update(double learning_r){}


}
