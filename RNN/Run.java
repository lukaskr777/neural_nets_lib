public class Run{


    public static void print(Object o){
        System.out.print(o);
    }

    public static void println(Object o){
        System.out.println(o);
    }
    public static void main(String[] args){


        // CHECK backprop for multiple layers
       Matrix m1 = new Matrix(1, 2);
       m1.set(0, 0, 1);
       m1.set(0, 1, 1);


        Matrix m2 = new Matrix(1, 2);
        m2.set(0, 0, 0);
        m2.set(0, 1, 1);

 

        Matrix m3 = new Matrix(1, 2);
        m3.set(0, 0, 1);
        m3.set(0, 1, 0);


        Matrix m4 = new Matrix(1, 2);
        m4.set(0, 0, 0);
        m4.set(0, 1, 0);



        Matrix t = new Matrix(1, 2);
        t.set(0, 0, 1);
        t.set(0, 1, 0);

        
        Matrix f = new Matrix(1, 2);
        f.set(0, 0, 0);
        f.set(0, 1, 1);


       CNN n = new CNN(new Layer[]{new InputLayer(2), new Dense(2, "signum"), new OutputLayer()},0.1);

       print(n);
      for(int i = 0; i != 10000; ++i){
          n.learnData(m1, t);
          n.learnData(m2, f);
          n.learnData(m3, f);
          n.learnData(m4, f);
      }
       print("x\nx\nx\nx\nx\n");
       print(n);

       print("\nreds:\n");
       print(n.classify(m1));
       print(n.classify(m3));
       print(n.classify(m2));
       print(n.classify(m4));
       
    }
}