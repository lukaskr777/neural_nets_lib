public class Relu implements SFunction {
    

    public double y(double x){
        if(x <= 0) return 0;
        return x;
    }

    public double dy(double x){
        if(x <= 0) return 0;
        return 1;
    }

    
}
