/**
 * Created by Dell on 2017-10-08.
 */
public final class Activation {

    public static final double sigmoid(double x){
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }

    public static final double sigmoidDerivative(double x){
        return sigmoid(x)*(1-sigmoid(x));
    }
}
