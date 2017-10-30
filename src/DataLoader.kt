import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by Dell on 2017-10-26.
 */

var dir = "C:\\Users\\Dell\\Downloads\\cyfry_zima_17-18\\cyfry_zima_17-18"

var sampleNumber = 0


fun loadData(): Matrix {
    File(dir).walkTopDown().forEach{
        if(it.toString() != dir) {
            var image = ImageIO.read(it)
            if (image.height == 10 && image.width == 7) {
                sampleNumber++
            }
        }
    }
    println("sample number "+sampleNumber)

    var finalResult = Matrix(sampleNumber, 70)
    var counter = 0
    File(dir).walkTopDown().forEach{
        if(it.toString() != dir) {
            var image = ImageIO.read(it)
            if (image.height == 10 && image.width == 7) {
                var sample = DoubleArray(image.height * image.width)
                for (x in 0..image.width-1) {
                    for (y in 0..image.height-1) {
                        var index = (x * image.height) + y
                        sample[index] = if (image.getRGB(x, y) == Color.BLACK.rgb) 1.0 else 0.0
                    }
                }
                finalResult.setElem(counter++, sample)
            }
        }
    }
    return finalResult
}

fun loadLabels(): Matrix{
    var finalResult = Matrix(sampleNumber, 10)
    var counter = 0
    File(dir).walkTopDown().forEach{
        if (it.toString() != dir) {
            var image = ImageIO.read(it)
            if (image.height == 10 && image.width == 7) {
                var label = Character.toString(it.toString().get(it.toString().length - 14)).toInt()
                finalResult.setElem(counter++, Vector.createBinaryValueVector(label))
            }
        }
    }
    return finalResult
}
