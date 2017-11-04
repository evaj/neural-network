import java.util.List;

/**
 * Created by Dell on 2017-10-28.
 */
public class Network {

    private Vector inputs;
    private List<Layer> layers;
    private int numberOfLayers;
    private Vector expectedValue;

    public Network(List<Layer> layers) {
        this.layers = layers;
        this.numberOfLayers = layers.size();
    }

    public void forwardPropagation() {
        Vector outputs = this.inputs;
        for (Layer layer : layers) {
            outputs = layer.calculateOutputs(outputs);
        }
    }

    public void backwardPropagation(){
        Layer lastLayer = layers.get(numberOfLayers - 1);
        Vector lastLayerErrors = new Vector(layers.get(numberOfLayers - 1).getNumberOfNeurons());

        Vector [] layerErrors = new Vector[numberOfLayers];

        if(lastLayerErrors.rows != expectedValue.rows)
            System.out.println("Number of neurons on last layer should match number of expected values!");

        for (int i = 0; i < lastLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = lastLayer.getNeurons().get(i);
            lastLayerErrors.setElem(i, Error.meanSquareDerivativeToOutput(expectedValue.getElem(i), neuron.getOutput()) * neuron.activationDerivative());
        }
        layerErrors[numberOfLayers-1] = lastLayerErrors;

        for (int i = numberOfLayers - 2; i >= 0; i--) {
            layerErrors[i] = layers.get(i).calculateErrors(layers.get(i + 1).getWeights(), layerErrors[i+1]);
        }

        for(int i = 0; i < numberOfLayers; i++){
            for(int j = 0 ; j < layers.get(i).getNumberOfNeurons(); j++){
                layers.get(i).getNeurons().get(j).updateWeightsAndBias(layerErrors[i].getElem(j));
            }
        }
    }

    public double calculateCost() {
        Layer lastLayer = layers.get(numberOfLayers - 1);
        double totalError = 0.;
        for (int i = 0; i < lastLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = lastLayer.getNeurons().get(i);
            totalError += Error.meanSquare(expectedValue.getElem(i), neuron.getOutput());
        }
        return currentError = totalError;
    }

    public double currentError;

    public int getNumberOfLayers() {
        return numberOfLayers;
    }

    public void setNumberOfLayers(int numberOfLayers) {
        this.numberOfLayers = numberOfLayers;
    }

    public Vector getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(Vector expectedValue) {
        this.expectedValue = expectedValue;
    }

    public Matrix getInputs() {
        return inputs;
    }

    public void setInputs(Vector inputs) {
        this.inputs = inputs;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public int predictedValue() {
        return 0;
    }
}
