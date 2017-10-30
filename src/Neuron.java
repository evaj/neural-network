/**
 * Created by Dell on 2017-10-09.
 */
abstract class Neuron {

    private Vector weights;
    private double bias;
    private Vector inputs;
    private double output;

    public Neuron(){
        bias = 0.0;
        output = 0.0;
    }

    public void updateWeightsAndBias(double error){
        bias -= Parameters.learningRate*error;
        weights = MatrixOperationKt.operate(Parameters.learningRate*error, inputs.elementwiseMultiply(weights),
                MatrixOperationKt.getSubtract()).transpose().getRow(0);}

    public Neuron(int numberOfInputs){
        this();
        inputs = new Vector(numberOfInputs);
        weights = Parameters.initWeights(numberOfInputs);
    }

    public Neuron(Vector weights, double bias) {
        this.weights = weights;
        this.bias = bias;
    }

    public double calculateOutput(){
        return this.output = calculateActivation(net(), 0);
    }

    public abstract double activationDerivative();

    abstract double calculateActivation(double value, double threshold);

    private double net(){
        return weights.transpose().multiply(this.inputs).getSingleValue() + this.bias;
    }

    public Vector getWeights() {
        return weights;
    }

    public void setWeights(Vector weights) {
        this.weights = weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public boolean equals(Neuron perceptron){
        if(perceptron.getWeights().getRows() != getWeights().getRows()
                || perceptron.getWeights().columns != perceptron.getWeights().columns)
            return false;
        for(int i = 0; i < weights.getRows(); i++){
            for(int j = 0; j < weights.columns ; j++){
                if(getWeights().getElem(i,j) == perceptron.getWeights().getElem(i, j))
                    return false;
            }
        }
        return perceptron.getBias() == getBias();
    }

    public Vector getInputs() {
        return inputs;
    }

    public void setInputs(Vector inputs) {
        this.inputs = inputs;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
}
