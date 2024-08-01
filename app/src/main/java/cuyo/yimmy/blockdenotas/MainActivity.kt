package cuyo.yimmy.blockdenotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import android.util.TypedValue
class MainActivity : AppCompatActivity() {

    private lateinit var textoEditable: EditText
    private lateinit var contenedor: LinearLayout
    private lateinit var imagenMenu: ImageView
    private lateinit var imagenOpciones: ImageView
    private lateinit var llTodasLasNotas: LinearLayout
    private lateinit var llPapelera: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducion)

        textoEditable = findViewById(R.id.textoEditable)
        contenedor = findViewById(R.id.contenedor)
        imagenMenu = findViewById(R.id.imagenMenu)
        imagenOpciones = findViewById(R.id.imagenOpciones)

        textoEditable.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                addTextView(textoEditable.text.toString())
                textoEditable.text.clear()
                true
            } else {
                false
            }
        }

        imagenMenu.setOnClickListener { verVentanaEmergente1(it) }
        imagenOpciones.setOnClickListener { verVentanaEmergente2(it) }
    }

    private fun addTextView(text: String) {
        val textView = TextView(this)
        textView.text = text
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(16, 16, 16, 16)

        textView.layoutParams = layoutParams
        textView.setBackgroundResource(R.drawable.border_completo)
        textView.setBackgroundColor(android.graphics.Color.BLACK)
        textView.setTextColor(android.graphics.Color.WHITE)
        textView.setPadding(50, 25, 50, 25)

        contenedor.addView(textView)
    }

    private fun verVentanaEmergente1(anchor: View) {
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.ventana_emergente, null)
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.showAsDropDown(anchor, -anchor.width / 2, 0)
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
        popupWindow.showAsDropDown(anchor, -anchor.width / 2, 0)

        llTodasLasNotas = popupView.findViewById(R.id.ll_todas_las_notas)
        llPapelera = popupView.findViewById(R.id.ll_papelera)

        llTodasLasNotas.setOnClickListener {
            selectOption(llTodasLasNotas, llPapelera, R.id.tv_todas_las_notas, R.id.tv_papelera)
        }
        llPapelera.setOnClickListener {
            selectOption(llPapelera, llTodasLasNotas, R.id.tv_papelera, R.id.tv_todas_las_notas)
        }
    }

    private fun selectOption(selected: LinearLayout, unselected: LinearLayout, selectedTextId: Int, unselectedTextId: Int) {
        selected.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        selected.findViewById<TextView>(selectedTextId).setTextColor(ContextCompat.getColor(this, R.color.white))
        unselected.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        unselected.findViewById<TextView>(unselectedTextId).setTextColor(ContextCompat.getColor(this, R.color.black))
    }
}
