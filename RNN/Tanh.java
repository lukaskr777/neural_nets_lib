public class Tanh implements SFunction {


    public double y(double x){
        return Math.tanh(x);
    }

    public double dy(double x){
        return 1 - Math.pow(y(x),2);
    }


    
}
