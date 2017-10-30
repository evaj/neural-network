import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017-10-28.
 */
public class Layer {

    List<Neuron> neurons;
    int numberOfNeurons;

    public Layer(int numberOfNeurons, List<Neuron> neurons){
        this.numberOfNeurons = numberOfNeurons;
        this.neurons = neurons;
    }

    public Vector calculateOutputs(Vector inputs){
        Vector outputs = new Vector(this.numberOfNeurons);
        for(int i = 0; i < numberOfNeurons; i++){
            neurons.get(i).setInputs(inputs);
            outputs.setElem(i, neurons.get(i).calculateOutput());
        }
        return outputs;
    }

    public Matrix getWeights(){
        Matrix weights = new Matrix(numberOfNeurons, neurons.get(0).getWeights().getRows());
        for(int i = 0; i < numberOfNeurons ; i++){
            weights.setElem(i, neurons.get(i).getWeights().toArray());
        }
        return weights;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public Vector calculateErrors(Matrix nextLayerWeights, Vector errors){
        Vector weightedErrors = nextLayerWeights.transpose().multiply(errors).transpose().getRow(0);
        Vector derivatives = new Vector(numberOfNeurons);
        for(int i = 0; i < numberOfNeurons ; i++){
            derivatives.setElem(i, neurons.get(i).activationDerivative());
        }
        Vector layerErrors = weightedErrors.elementwiseMultiply(derivatives).transpose().getRow(0);
        for(int i = 0; i < numberOfNeurons; i++){
            neurons.get(i).updateWeightsAndBias(layerErrors.getElem(i));
        }
        return layerErrors;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public void setNumberOfNeurons(int numberOfNeurons) {
        this.numberOfNeurons = numberOfNeurons;
    }
}
