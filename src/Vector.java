import kotlin.ranges.IntRange;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Dell on 2017-10-07.
 */
public class Vector extends Matrix {

    public Vector(int size){
        super(size, 1);
    }

    public Vector(Double [] values){
        this(unwrapDoubleArray(values));
    }

    public Vector(double [] values){
        super(values.length, 1);
        this.values = new double [values.length][1];
        IntStream.range(0, values.length).forEach( index -> this.values[index] = new double [] {values[index]});
    }

    public Vector(Vector other){
        this(other.toArray());
    }

    public double getElem(int x) {
        return values[x][0];
    }

    public void setElem(int x, double val) {
        values[x][0]=val;
    }

    private static double [] unwrapDoubleArray(Double [] array){
        double [] result = new double[array.length];
        IntStream.range(0, array.length)
                .forEach( row -> result[row] = array[row].doubleValue());
        return result;
    }

    public double[] toArray(){
        return IntStream.range(0, rows)
                .mapToDouble( index -> this.getElem(index)).toArray();
    }

    public static double [] createBinaryValueVector(int value){
        double [] array = new double[10];
        array[value] = 1;
        return array;
    }
}
