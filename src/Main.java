import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017-10-26.
 */
public class Main {

    public static void main(String[] args) {
        Matrix data = DataLoaderKt.loadData();
        Matrix labels = DataLoaderKt.loadLabels();
        System.out.println(labels);
        List<Neuron> neurons = new ArrayList<>();

        for(int i = 0 ; i < 5 ; i++){
            neurons.add(new SigmoidNeuron(70));
        }
        Layer layer1 = new Layer(5, neurons);
        List<Neuron> neurons2 = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            neurons2.add(new SigmoidNeuron(layer1.getNumberOfNeurons()));
        }
        Layer outputLayer = new Layer(10, neurons2);
        List<Layer> layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(outputLayer);
        Network network = new Network(data.getRow(0), layers, labels.getRow(0));

        for(Layer layer : network.getLayers()) {
            for(Neuron neuron : layer.getNeurons()) {
                neuron.setBias(1.0);
                for(int i = 0; i < neuron.getWeights().getRows(); i++) {
                    neuron.getWeights().setElem(i, 0.5);
                }
            }
        }

        Optimiser.train(network, data, labels);
    }
}
