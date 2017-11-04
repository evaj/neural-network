import kotlin.Function;
import kotlin.ranges.IntRange;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Created by Dell on 2017-10-07.
 */
public class Matrix {
    double [][] values;
    int rows;
    int columns;

    public Matrix(){};

    public Matrix(int size) {
        this(size, size);
    }

    public Matrix(Double [][] values){
        init(unwrapDoubleArray(values));
    }

    private static double [][] unwrapDoubleArray(Double [][] array){
        double [][] result = new double[array.length][array[0].length];
        IntStream.range(0, array.length)
                .forEach( row -> IntStream.range(0, array[0].length)
                        .forEach( col -> result[row][col] = array[row][col].doubleValue()));
        return result;
    }

    public Matrix(double [][] values) {
        init(values);
    }

    private void init(double [][] values){
        if(values == null)
            throw new IllegalArgumentException("Array cannot be null");
        rows = values.length;
        columns = values[0].length;
        //filling up to rectangular matrix
        int maxLength = Arrays.stream(values).mapToInt(row -> row.length).max().getAsInt();
        this.values = Arrays.stream(values)
                .map((double[] row) -> Arrays.copyOf(row, maxLength))
                .toArray(double[][]::new);
    }

    public Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        values = new double[rows][columns];
    }

    public void clear() {
        IntStream.range(0, rows)
                .forEach( row -> IntStream.range(0, columns)
                        .forEach( col -> this.setElem(row, col, 0)));
    }

    public double getElem(int x, int y) {
        return values[x][y];
    }

    public void setElem(int x, int y, double val) {
        values[x][y]=val;
    }

    public void setElem(int x, double [] values){
        if(values.length != this.columns)
            throw new IllegalArgumentException("Bad size!");
        IntStream.range(0, values.length).forEach(iter -> {
            this.values[x][iter] = values[iter];
        });
    }

    public void flatten(){
        double [][] temp = values;

    }

    public Matrix multiply(Matrix matrix) throws IllegalArgumentException {
        if(this.columns != matrix.rows)
            throw new IllegalArgumentException("Bad matrix size!");
        Matrix result = new Matrix(
                Arrays.stream(this.values).map(r ->
                    IntStream.range(0, matrix.values[0].length).mapToDouble(i ->
                        IntStream.range(0, matrix.values.length).mapToDouble(j -> r[j] * matrix.values[j][i]).sum()
                    ).toArray()).toArray(double[][]::new)
        );
        return result;
    }

    public Vector getRow(int row){
        if(row < 0 || row > rows)
            throw new IllegalArgumentException("Row number out of range");
        return new Vector(values[row]);
    }

    public Matrix transpose() {
        Matrix transposed = new Matrix(this.columns, this.rows);
        IntStream.range(0, rows)
                .forEach(row -> IntStream.range(0, columns)
                        .forEach(col -> transposed.setElem(col, row, values[row][col])));
        return transposed;
    }

    public Matrix subtract(Matrix matrix){
        return MatrixOperationKt.operateByElement(this, matrix, MatrixOperationKt.getSubtract());
    }

    public Matrix add(Matrix matrix){
        return MatrixOperationKt.operateByElement(this, matrix, MatrixOperationKt.getAdd());
    }

    public Matrix elementwiseMultiply(Matrix matrix){
        return MatrixOperationKt.operateByElement(this, matrix, MatrixOperationKt.getMultiply());
    }

    public Matrix divide(Matrix matrix){
        return MatrixOperationKt.operateByElement(this, matrix, MatrixOperationKt.getDivide());
    }

    public double getSingleValue(){
        if(rows == 1 && columns == 1)
            return values[0][0];
        else throw new IllegalStateException("Matrix is not a 1x1 matrix.");
    }

    public String toString() {
        return Arrays.deepToString(values);
    }

    public Matrix getFirstColumns(int numberOfColumns){
        if(numberOfColumns > this.columns)
            throw new IllegalArgumentException("Matrix doesn't have this many columns.");
        Matrix matrix = new Matrix(this.rows, numberOfColumns);
        IntStream.range(0, rows)
                .forEach(row -> IntStream.range(0, numberOfColumns)
                        .forEach(col -> matrix.setElem(row, col, values[row][col])));
        return matrix;
    }

    public Matrix getLastColumns(int numberOfColumns){
        if(numberOfColumns > this.columns)
            throw new IllegalArgumentException("Matrix doesn't have this many columns.");
        Matrix matrix = numberOfColumns == 1 ? new Vector(this.rows) : new Matrix(this.rows, numberOfColumns);
        IntStream.range(0, rows)
                .forEach(row -> IntStream.range(this.columns - numberOfColumns, columns)
                        .forEach(col -> matrix.setElem(row, col - (columns - numberOfColumns), values[row][col])));
        return matrix;
    }

    public Matrix getFirstRows(int numberOfRows){
        if(numberOfRows > this.rows)
            throw new IllegalArgumentException("Matrix doesn't have this many columns.");
        Matrix matrix = new Matrix(numberOfRows, columns);
        IntStream.range(0, numberOfRows)
                .forEach(row -> IntStream.range(0, columns)
                        .forEach(col -> matrix.setElem(row, col, values[row][col])));
        return matrix;
    }
    public Matrix getLastRows(int numberOfRows){
        if(numberOfRows > this.rows)
            throw new IllegalArgumentException("Matrix doesn't have this many columns.");
        Matrix matrix = new Matrix(numberOfRows, this.columns);
        IntStream.range(rows - numberOfRows, rows)
                .forEach(row -> IntStream.range(0, columns)
                        .forEach(col -> matrix.setElem(row - (rows - numberOfRows), col, values[row][col])));
        return matrix;
    }

    public Matrix joinVertically(Matrix matrix){
        if(columns != matrix.columns)
            throw new IllegalArgumentException("Column numbers must match!");
        Matrix joined = new Matrix(this.rows + matrix.rows, this.columns);
        IntStream.range(0, joined.rows)
                .forEach(row -> IntStream.range(0, joined.columns)
                    .forEach(col -> {
                        if(row < rows)
                            joined.setElem(row, col, values[row][col]);
                        else
                            joined.setElem(row, col, matrix.values[row - rows][col]);
                    }));
        return joined;
    }

    public Matrix joinHorizontally(Matrix matrix) {
        if(rows != matrix.rows)
            throw new IllegalArgumentException("Row numbers must match!");
        Matrix joined = new Matrix(this.rows, this.columns + matrix.columns);
        IntStream.range(0, joined.rows)
                .forEach(row -> IntStream.range(0, joined.columns)
                        .forEach(col -> {
                            if(col < columns)
                                joined.setElem(row, col, values[row][col]);
                            else
                                joined.setElem(row, col, matrix.values[row][col - columns]);
                        }));
        return joined;
    }

    public void shuffle()
    {
        int index;
        double [] temp;
        Random random = new Random();
        for (int i = values.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = values[index];
            values[index] = values[i];
            values[i] = temp;
        }
    }

    public void visualiseSample(){
        for(int i = 0 ; i < 70 ; i++) {
            System.out.print((int)values[i][0]+" ");
            if(i%7 == 6)
                System.out.println();
        }
    }


    public int getRows(){
        return rows;
    }
}

