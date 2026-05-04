public class Signum implements SFunction {


    public double y(double x){

        return 1.0/(1.0 + Math.exp(-x));
    }

    public double dy(double x){
        return y(x);
    }
    
}
