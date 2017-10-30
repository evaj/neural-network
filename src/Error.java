/**
 * Created by Dell on 2017-10-08.
 */
public final class Error {

    public static int binary(double expected, double received){
        return (int)Math.round(expected - received);
    }
    public static double meanSquareDerivative(double expected, double net){
        return 2*(expected - net);
    }
    public static double meanSquare(double expected, double net){
        return ((expected-net)*(expected-net))/2;
    }

    public static double meanSquareDerivativeToOutput(double expected, double output){
        return output - expected;
    }
}
