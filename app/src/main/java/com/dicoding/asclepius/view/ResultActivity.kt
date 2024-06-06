import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var tflite: Interpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra("image_uri")
        val uri = Uri.parse(imageUri)
        binding.resultImage.setImageURI(uri)

        try {
            // Load the TensorFlow Lite model
            tflite = Interpreter(loadModelFile("cancer_classification.tflite"))

            // Perform the prediction
            val bitmap = getBitmapFromUri(uri)
            val prediction = classifyImage(bitmap)

            // Display the prediction and confidence score
            binding.resultText.text = "Prediction: ${prediction.first}\nConfidence: ${prediction.second}"
        } catch (e: Exception) {
            // Show error message if image analysis fails
            showErrorToast()
        }
    }

    private fun loadModelFile(modelName: String): ByteBuffer {
        val fileDescriptor = assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }


    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun classifyImage(bitmap: Bitmap): Pair<String, Float> {
        // Resize and normalize the input image to match the model input requirements
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val input = ByteBuffer.allocateDirect(224 * 224 * 3 * 4).order(ByteOrder.nativeOrder())
        for (x in 0 until 224) {
            for (y in 0 until 224) {
                val pixel = resizedBitmap.getPixel(x, y)
                input.putFloat((pixel shr 16 and 0xFF) / 255.0f)
                input.putFloat((pixel shr 8 and 0xFF) / 255.0f)
                input.putFloat((pixel and 0xFF) / 255.0f)
            }
        }

        // Model output placeholder
        val output = ByteBuffer.allocateDirect(1 * 4).order(ByteOrder.nativeOrder())
        output.rewind()

        // Run inference
        tflite.run(input, output)

        // Interpret the result
        output.rewind()
        val confidence = output.float
        val prediction = if (confidence > 0.5) "Positive" else "Negative"

        return Pair(prediction, confidence)
    }

    private fun showErrorToast() {
        Toast.makeText(this, "Image analysis failed. Please try again.", Toast.LENGTH_SHORT).show()
    }
}
