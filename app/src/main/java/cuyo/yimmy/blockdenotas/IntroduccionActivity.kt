package cuyo.yimmy.blockdenotas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroduccionActivity : AppCompatActivity() {

    private lateinit var contenedor: LinearLayout
    private lateinit var botonBorrar: Button
    private lateinit var botonEditar: Button
    private lateinit var textoEditable: EditText

    private val textosEliminados = ArrayList<String>()
    private var textoSeleccionado: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducion)

        contenedor = findViewById(R.id.contenedor)
        botonBorrar = findViewById(R.id.botonBorrar)
        botonEditar = findViewById(R.id.botonEditar)
        textoEditable = findViewById(R.id.textoEditable)

        botonBorrar.setOnClickListener {
            borrarTexto()
        }

        botonEditar.setOnClickListener {
            editarTexto()
        }

        // Ejemplo de agregar TextViews
        agregarTexto("Texto de ejemplo")
        agregarTexto("hola, eres muy buena persona")
        agregarTexto("comprar comida")
        agregarTexto("me gusto verte")
    }

    private fun agregarTexto(texto: String) {
        val textView = TextView(this)
        textView.text = texto
        textView.setPadding(10, 10, 10, 10)
        textView.setBackgroundResource(R.drawable.border_completo) // Estilo de borde para selección
        textView.setOnClickListener {
            seleccionarTexto(textView)
        }
        contenedor.addView(textView)
    }

    private fun seleccionarTexto(textView: TextView) {
        // Remover selección previa
        textoSeleccionado?.setBackgroundResource(R.drawable.border_completo) // Estilo normal
        // Seleccionar nuevo
        textoSeleccionado = textView
        textView.setBackgroundResource(R.drawable.border_seleccionado) // Estilo seleccionado
    }

    private fun borrarTexto() {
        textoSeleccionado?.let { textoView ->
            val texto = textoView.text.toString()
            textosEliminados.add(texto)
            contenedor.removeView(textoView)
            textoSeleccionado = null

            // Guardar los textos eliminados en SharedPreferences
            val sharedPreferences = getSharedPreferences("textosEliminados", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putStringSet("textosEliminados", textosEliminados.toSet())
            editor.apply()
        }
    }

    private fun editarTexto() {
        textoSeleccionado?.let { textoView ->
            textoEditable.setText(textoView.text.toString())
            textoEditable.requestFocus()
            // Aquí puedes agregar lógica adicional para guardar los cambios
        }
    }
}
