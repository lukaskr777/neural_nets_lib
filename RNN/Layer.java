public abstract class Layer {
    
    protected int neurons;

    protected Matrix output_vector;
    protected Matrix weights, d_weights;
    protected Matrix bias_nodes, d_bias_nodes;
    

    protected Layer last_layer, next_layer;
    protected boolean frontproped = false;

    public Matrix getOutputVector(){
        return this.output_vector;
    } 

    public void connect(Layer last_layer){
        this.last_layer = last_layer;
        last_layer.next_layer = this;
    }

    public boolean isConnected(){
        return last_layer != null && next_layer != null;
    }

    public abstract Matrix frontprop();
    public abstract Matrix backprop();
    public abstract void update(double learning_r);


}
