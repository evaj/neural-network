import java.util.Random;

/**
 * Created by Dell on 2017-10-28.
 */
public class Parameters {

    static double upperBound = 0.5;
    static double lowerBound = -0.5;
    static double learningRate = 0.5;
    static int epochs = 20;

    public static Vector initWeights(int size){
        Random r = new Random();
        double [] array = new double[size];
        for(int i = 0; i < size ; i++) {
            array[i] = lowerBound + (upperBound - lowerBound) * r.nextDouble();
        }
        return new Vector(array);
    }
}
