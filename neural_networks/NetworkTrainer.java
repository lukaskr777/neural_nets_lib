import java.io.File;


public class NetworkTrainer {
    static NeuronMap map;

    static int all =0;
    static int correct = 0;

    public static void train(NeuronMap in_map,String train_src){
        map = in_map;
        

        File dir = new File(train_src);   
        String[] files = dir.list();

        for(int x = 100; x < 110; ++x){
            String img = files[x];
            System.out.println(img);
            String[] split = img.split("_");
            //System.out.println(split[1]);
            ++all;
            if(map.recognizeAndLearnImage(train_src + img, Integer.parseInt(split[1]))) ++correct;

            
        }
        System.out.println("All: "+all+" correct: "+correct);

    }


}
