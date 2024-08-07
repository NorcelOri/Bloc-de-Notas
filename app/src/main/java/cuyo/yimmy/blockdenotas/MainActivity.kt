package cuyo.yimmy.blockdenotas

import android.content.Intent
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

        val texts = loadTexts()
        for (text in texts) {
            addTextView(text)
        }

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

        val texts = mutableListOf<String>()
        for (i in 0 until contenedor.childCount) {
            val child = contenedor.getChildAt(i) as TextView
            texts.add(child.text.toString())
        }
        saveTexts(texts)
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

        llTodasLasNotas = popupView.findViewById(R.id.ll_todas_las_notas)
        llPapelera = popupView.findViewById(R.id.ll_papelera)

        llTodasLasNotas.setOnClickListener {
            selectOption(llTodasLasNotas, llPapelera, R.id.tv_todas_las_notas, R.id.tv_papelera)
            // Aquí puedes añadir el código necesario para mostrar el contenido de "Todas las notas" si es necesario
            popupWindow.dismiss()
        }

        llPapelera.setOnClickListener {
            selectOption(llPapelera, llTodasLasNotas, R.id.tv_papelera, R.id.tv_todas_las_notas)
            val intent = Intent(this, PapeleraActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchor, -anchor.width / 2, 0)
    }

    private fun selectOption(selected: LinearLayout, unselected: LinearLayout, selectedTextId: Int, unselectedTextId: Int) {
        selected.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        selected.findViewById<TextView>(selectedTextId).setTextColor(ContextCompat.getColor(this, R.color.white))
        unselected.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        unselected.findViewById<TextView>(unselectedTextId).setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun saveTexts(texts: List<String>) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("texts_size", texts.size)
        for (i in texts.indices) {
            editor.putString("text_$i", texts[i])
        }
        editor.apply()
    }

    private fun loadTexts(): List<String> {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val size = sharedPreferences.getInt("texts_size", 0)
        val texts = mutableListOf<String>()
        for (i in 0 until size) {
            val text = sharedPreferences.getString("text_$i", null)
            if (text != null) {
                texts.add(text)
            }
        }
        return texts
    }
}
