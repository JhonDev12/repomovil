package com.auxilitos.mis_primeros_auxilitos.games

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import com.auxilitos.mis_primeros_auxilitos.MainActivity
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Random

@Suppress("DEPRECATION")
class AparecerObjetosAuxilitos : AppCompatActivity() {

    private lateinit var btnRegresar: CircleImageView

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var layout: RelativeLayout

    private var maxXOffset: Float = 0f
    private var maxYOffset: Float = 0f

    private val random = Random()
    private var lastTouchedImage: ImageView? = null

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No se necesita implementar en este caso
        }

        override fun onSensorChanged(event: SensorEvent?) {
            // Si se está arrastrando una imagen, no actualizamos las coordenadas por el acelerómetro
            if (lastTouchedImage == null && event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                for (i in 0 until layout.childCount) {
                    val imageView = layout.getChildAt(i) as? ImageView
                    imageView?.let {
                        val xOffset = it.x - event.values[0]
                        val yOffset = it.y + event.values[1]

                        val newXOffset = xOffset.coerceIn(0f, maxXOffset)
                        val newYOffset = yOffset.coerceIn(0f, maxYOffset)

                        it.x = newXOffset
                        it.y = newYOffset
                    }
                }
            }
        }
    }

    private val toast = ToastCustom()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_auxilito)

        btnRegresar = findViewById(R.id.btn_return)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        layout = findViewById(R.id.layout)

        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        maxXOffset = display.width.toFloat()
        maxYOffset = display.height.toFloat()

        layout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Crear y configurar la nueva imagen
                    val newImageId = getRandomImageResource()
                    val newImage = ImageView(this)
                    newImage.setImageResource(newImageId)
                    newImage.layoutParams = RelativeLayout.LayoutParams(100, 100)
                    newImage.x = event.x
                    newImage.y = event.y

                    layout.addView(newImage)
                    lastTouchedImage = newImage
                }
                MotionEvent.ACTION_MOVE -> {
                    // Arrastrar la imagen si fue tocada previamente
                    lastTouchedImage?.x = event.x - lastTouchedImage!!.width / 2
                    lastTouchedImage?.y = event.y - lastTouchedImage!!.height / 2
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Limpiar la referencia a la última imagen tocada
                    lastTouchedImage = null
                }
            }
            true
        }

        btnRegresar.setOnClickListener {
            /*val fragment = DashboardFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.navigation_dashboard, fragment)
            transaction.addToBackStack(null)
            transaction.commit()*/
            startActivity(Intent(this, MainActivity::class.java))
            toast.toastSuccess(this, "Mis Primeros Auxilitos", "😉😉😉😉😉😉😉😉")
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun getRandomImageResource(): Int {
        // Aquí deberías proporcionar una lista de IDs de recursos de imágenes diferentes
        val imageResources = listOf(R.drawable.botiquin, R.drawable.nino, R.drawable.pildora, R.drawable.cura)
        return imageResources[random.nextInt(imageResources.size)]
    }


}