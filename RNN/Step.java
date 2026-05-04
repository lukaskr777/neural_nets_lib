public class Step implements SFunction {


    public double y(double x){

        if(x > 0) return 1;
        if(x < 0) return -1;
        return 0;
    }

    public double dy(double x){
        return 0; // no slope
    }
    
}
