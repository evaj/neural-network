import java.util.List;

/**
 * Created by Dell on 2017-10-28.
 */
public class Network {

    private Vector inputs;
    private List<Layer> layers;
    private int numberOfLayers;
    private Vector expectedValue;

    public Network(Vector inputs, List<Layer> layers) {
        this.inputs = inputs;
        this.layers = layers;
        this.numberOfLayers = layers.size();
    }

    public Network(Vector inputs, List<Layer> layers, Vector expectedValue) {
        this.inputs = inputs;
        this.layers = layers;
        this.numberOfLayers = layers.size();
        this.expectedValue = expectedValue;
        if (layers.get(numberOfLayers - 1).numberOfNeurons != expectedValue.rows)
            throw new IllegalArgumentException("Number of neurons in last layer should match number of expected outputs!");
    }

    public void forwardPropagation() {
        Vector outputs = this.inputs;
        for (Layer layer : layers) {
            outputs = layer.calculateOutputs(outputs);
        }
    }

    public double backwardPropagation() {
        Layer lastLayer = layers.get(numberOfLayers - 1);
        Vector lastLayerErrors = new Vector(layers.get(numberOfLayers - 1).getNumberOfNeurons());

        for (int i = 0; i < lastLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = lastLayer.getNeurons().get(i);
            lastLayerErrors.setElem(i, Error.meanSquareDerivativeToOutput(expectedValue.getElem(i), neuron.getOutput()) * neuron.activationDerivative());
        }

        for (int i = numberOfLayers - 2; i >= 0; i--) {
            Vector errors = layers.get(i).calculateErrors(layers.get(i + 1).getWeights(), lastLayerErrors);
        }

        for (int i = 0; i < lastLayer.getNumberOfNeurons(); i++) {
            lastLayer.getNeurons().get(i).updateWeightsAndBias(lastLayerErrors.getElem(i));
        }

        double totalError = 0.;
        for (int i = 0; i < lastLayerErrors.rows; i++) {
            totalError += lastLayerErrors.getElem(i);
        }
        return 0;
    }

    public double calculateCost() {
        Layer lastLayer = layers.get(numberOfLayers - 1);
        Vector lastLayerErrors = new Vector(layers.get(numberOfLayers - 1).getNumberOfNeurons());

        for (int i = 0; i < lastLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = lastLayer.getNeurons().get(i);
            lastLayerErrors.setElem(i, Error.meanSquare(expectedValue.getElem(i), neuron.getOutput()) * neuron.activationDerivative());
        }

        double totalError = 0.;
        for (int i = 0; i < lastLayerErrors.rows; i++) {
            totalError += lastLayerErrors.getElem(i);
        }
        return currentError = totalError / lastLayerErrors.rows;
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
