import java.util.stream.IntStream

/**
 * Created by Dell on 2017-10-08.
 */


val add = fun (x: Double, y: Double): Double {
    return x+y
}
val subtract = fun (x: Double, y: Double): Double {
    return x-y
}
val multiply = fun (x: Double, y: Double): Double {
    return x*y
}
val divide = fun (x: Double, y: Double): Double {
    return x/y
}
fun Matrix.operateByElement(matrix: Matrix, function: (Double, Double) -> Double): Matrix {
    if (this.rows != matrix.rows || this.columns != matrix.columns)
        throw IllegalArgumentException("Matrix sizes should match")
    val modified = Matrix(rows, columns)
    IntStream.range(0, rows)
            .forEach { row ->
                IntStream.range(0, columns)
                        .forEach { col ->
                            modified.setElem(row, col, function(values[row][col], matrix.values[row][col]))}
            }
    return modified
}

fun Double.operate(matrix: Matrix, function: (Double, Double) -> Double): Matrix {
    val modified = Matrix(matrix.rows, matrix.columns)
    IntStream.range(0, matrix.rows)
            .forEach { row ->
                IntStream.range(0, matrix.columns)
                        .forEach { col ->
                            modified.setElem(row, col, function(this, matrix.values[row][col]))}
            }
    return modified
}