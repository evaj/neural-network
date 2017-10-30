import java.util.List;

/**
 * Created by Dell on 2017-10-29.
 */
public class Optimiser {

    public static void train(Network network, Matrix data, Matrix labels){
        int epochs = 200;
        for(int i = 0; i < epochs; i++){
            Matrix allData = data.joinHorizontally(labels);
            allData.shuffle();
            data = allData.getFirstColumns(data.columns);
            labels = allData.getLastColumns(labels.columns);
            for(int j = 0; j< data.rows; j++){
                network.setInputs(data.getRow(j));
                network.setExpectedValue(labels.getRow(j));
                network.forwardPropagation();
                network.backwardPropagation();
            }
            System.out.println("Epoch "+i+" - "+network.currentError);
            List<Neuron> neuronList = network.getLayers().get(network.getNumberOfLayers()-1).getNeurons();
            for(Neuron n: neuronList){
                System.out.println(n.getOutput());
            }
        }
        System.out.println(labels.getRow(702));
        network.setInputs(data.getRow(702));
        network.forwardPropagation();
        List<Neuron> neuronList = network.getLayers().get(network.getNumberOfLayers()-1).getNeurons();
        for(Neuron n: neuronList){
            System.out.println(n.getOutput());
        }

        System.out.println(labels.getRow(220));
        network.setInputs(data.getRow(220));
        network.forwardPropagation();
        neuronList = network.getLayers().get(network.getNumberOfLayers()-1).getNeurons();
        for(Neuron n: neuronList){
            System.out.println(n.getOutput());
        }
    }
}
