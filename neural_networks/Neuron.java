

// neuron that holds initial value of the pixel of the picture of the number that is drawn
interface Neuron{

    public double getValue();
}

class TopNeuron implements Neuron{

    public TopNeuron(double val){
        value = val;
    }
    public double getValue(){ return value;}
    public void setValue(double val) { value = val; }

    private double value;
}

class DescendantNeuron implements Neuron{
    
    public DescendantNeuron(int incoming_neurons, byte number){
        this.number = number;
        relations = new double[incoming_neurons];
    }

    public boolean initRelation(int index, double val){
        if(index < 0 || index >= relations.length) return false;
        relations[index] = val;
        return true;
    }
    public boolean changeRelation(int index, double change){
        return initRelation(index, change+relations[index]);
    }

    public boolean initValue(TopNeuron[] toplevel_neurons){
        if(toplevel_neurons.length != relations.length) return false;
        setNeuronValue(toplevel_neurons);
        return true;
    }

    public double getValue(){ return value; }

    private void setNeuronValue(TopNeuron[] toplevel_neurons){
        value = 0; // reset before creating new value
        for(int x = 0; x != toplevel_neurons.length; ++x){
            value += toplevel_neurons[x].getValue()*relations[x]; // for every neuron value we calculate VALUE, based on the stregth of relation to this neuron
        }
    }
    public double[] getRelations(){return relations;}

    public double getRelationValue(int index){
        if(index < 0 || index >= relations.length){
            System.out.println("OUT OF BOUNDS[ "+index+" ]");
            return 0;
        }
        return relations[index];
    }

    public int getSize(){ return relations.length;};

    public int getNumber(){ return number;}

    byte number;
    private double relations[];
    private double value;
}
