package cuyo.yimmy.blockdenotas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.util.TypedValue

class PapeleraActivity : AppCompatActivity() {

    private lateinit var imagenOpciones: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana_papelera)

        imagenOpciones = findViewById(R.id.imagenOpciones)

        imagenOpciones.setOnClickListener {
            verVentanaEmergente2(it)
        }
    }

    private fun verVentanaEmergente2(anchor: View) {
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.ventana_emergente_2, null)
        val heightInDp = 550
        val heightInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            heightInPx,
            true
        )

        val papeleraOption = popupView.findViewById<LinearLayout>(R.id.ll_papelera)
        papeleraOption.setOnClickListener {
            popupWindow.dismiss() // Cierra el popup
        }

        val todasLasNotasOption = popupView.findViewById<LinearLayout>(R.id.ll_todas_las_notas)
        todasLasNotasOption.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss() // Cierra el popup
        }

        popupWindow.showAsDropDown(anchor, -anchor.width / 2, 0)
    }
}
