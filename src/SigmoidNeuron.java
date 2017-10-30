/**
 * Created by Dell on 2017-10-26.
 */
public class SigmoidNeuron extends Neuron {

    public SigmoidNeuron(int numberOfInputs){
        super(numberOfInputs);
    }

    public double calculateActivation(double value, double threshold){
        return Activation.sigmoid(value);
    }

    public double activationDerivative(){
        return Activation.sigmoidDerivative(this.getOutput());
    }
}
