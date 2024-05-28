package cuyo.yimmy.blockdenotas

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    private lateinit var textoEditable:  EditText
    private lateinit var contenedor: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducion)

        textoEditable=findViewById(R.id.textoEditable)
        contenedor=findViewById(R.id.contenedor)


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
}