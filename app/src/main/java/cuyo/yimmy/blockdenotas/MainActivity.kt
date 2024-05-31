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

class MainActivity : AppCompatActivity() {

    private lateinit var textoEditable:  EditText
    private lateinit var contenedor: LinearLayout
    private lateinit var imagenMenu: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducion)

        textoEditable = findViewById(R.id.textoEditable)
        contenedor = findViewById(R.id.contenedor)
        imagenMenu = findViewById(R.id.imagenMenu)

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
        imagenMenu.setOnClickListener{ verVentana(it)}
    }

    private fun addTextView(text:String){
        val textView = TextView(this)
        textView.text = text
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,)

        layoutParams.setMargins(16, 16, 16, 16)

        textView.setLayoutParams(layoutParams)
        textView.setBackgroundResource(R.drawable.border_completo)
        textView.setBackgroundColor(android.graphics.Color.BLACK)
        textView.setTextColor(android.graphics.Color.WHITE)
        textView.setPadding(50, 25, 50, 25)

        contenedor.addView(textView)
    }

    private fun verVentana(anchor: View){
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.ventana_emergente, null)

        val popupWindow = PopupWindow(popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true)

        popupWindow.showAsDropDown(anchor, -anchor.width / 2, 0)
    }
}