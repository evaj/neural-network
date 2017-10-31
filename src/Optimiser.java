import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2017-10-29.
 */
public class Optimiser {
    public static int getIndexOfMaxElement(List<Double> list) {
        int maxIndex = 0;
        for (int i = 1; i < list.size(); i++) {
            double newnumber = list.get(i);
            if ((newnumber > list.get(maxIndex))) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int getIndexOfMaxElement(Vector list) {
        int maxIndex = 0;
        for (int i = 1; i < list.rows; i++) {
            double newnumber = list.getElem(i);
            if ((newnumber > list.getElem(maxIndex))) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void train(Network network, Matrix data, Matrix labels) {
        int epochs = 20;
        for (int i = 0; i < epochs; i++) {
            Matrix allData = data.joinHorizontally(labels);
//            allData.shuffle();
            data = allData.getFirstColumns(data.columns);
            labels = allData.getLastColumns(labels.columns);
            for (int j = 0; j < data.rows; j++) {
                network.setInputs(data.getRow(j));
                network.setExpectedValue(labels.getRow(j));
                network.forwardPropagation();
                network.backwardPropagation();
            }

            int accuracy = 0;
            double cost = 0.0;

            for (int j = 0; j < data.rows; j++) {
                network.setInputs(data.getRow(j));
                network.setExpectedValue(labels.getRow(j));
                network.forwardPropagation();

                List<Neuron> neuronList = network.getLayers().get(network.getNumberOfLayers() - 1).getNeurons();
                List<Double> outputs = new ArrayList<>();

                for (Neuron n : neuronList) {
                    outputs.add(n.getOutput());
                }

                int expectedClass = getIndexOfMaxElement(labels.getRow(j));
                int predictedClass = getIndexOfMaxElement(outputs);

                if(expectedClass == predictedClass) {
                    accuracy++;
                }

                cost += network.calculateCost();
            }


            System.out.println("Accuracy = " + accuracy + " / " + 1744);
            System.out.println("Epoch " + i + " - " + cost / 1744.0);
//            List<Neuron> neuronList = network.getLayers().get(network.getNumberOfLayers() - 1).getNeurons();
//            for (Neuron n : neuronList) {
//                System.out.println(n.getOutput());
//            }
        }
//        System.out.println(labels.getRow(702));
//        network.setInputs(data.getRow(702));
//        network.forwardPropagation();
//        List<Neuron> neuronList = network.getLayers().get(network.getNumberOfLayers() - 1).getNeurons();
//        for (Neuron n : neuronList) {
//            System.out.println(n.getOutput());
//        }
//
//        System.out.println(labels.getRow(220));
//        network.setInputs(data.getRow(220));
//        network.forwardPropagation();
//        neuronList = network.getLayers().get(network.getNumberOfLayers() - 1).getNeurons();
//        for (Neuron n : neuronList) {
//            System.out.println(n.getOutput());
//        }
    }
}
