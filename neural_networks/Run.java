import java.util.Arrays;

class Run{

    static final String train_data_src = "./train_samples/train-images.idx3-ubyte";
    static final String train_data_labels_src = "./train_samples/train-labels.idx1-ubyte";
    static final String train_data_out=  "./train_samples_db/";


    public static void print(Object o){
        System.out.print(o);
    }
    public static void main(String[] args){

      Convolutional n = new Convolutional(new int[]{2,2,2});
    
      boolean correct = false;
      int learn_iter = 0;
      while(!correct){
          correct = true;
          if(!n.train(new double[]{1,1}, new double[]{-1,1})) correct = false;
          if(!n.train(new double[]{-1,-1}, new double[]{-1,1})) correct = false;
          if(!n.train(new double[]{1,-1}, new double[]{1,-1})) correct = false;
          if(!n.train(new double[]{-1,1}, new double[]{1,-1})) correct = false;
          ++learn_iter;


          if(learn_iter > 10000){
            
            n.reweight();
            // print(Arrays.toString(n.getOutput(new double[]{1,1})) + "\n");
            // print(Arrays.toString(n.getOutput(new double[]{-1,-1})) + "\n");
            // print(Arrays.toString(n.getOutput(new double[]{-1,1})) + "\n");
            // print(Arrays.toString(n.getOutput(new double[]{1,-1})) + "\n");
          //  print("-------------\n");
            learn_iter = 0;
            // try{
            //     Thread.sleep(100);
            // }catch(Exception e){}
          }
      }
      print("in " + learn_iter + " iterations\n");

      print(Arrays.toString(n.getOutput(new double[]{1,1})) + "\n");
      print(Arrays.toString(n.getOutput(new double[]{-1,-1})) + "\n");
      print(Arrays.toString(n.getOutput(new double[]{-1,1})) + "\n");
      print(Arrays.toString(n.getOutput(new double[]{1,-1})) + "\n");
    }
}