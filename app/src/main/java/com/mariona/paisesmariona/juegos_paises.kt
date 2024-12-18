package com.mariona.paisesmariona

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mariona.paisesmariona.classes.Paises
import com.mariona.paisesmariona.classes.cPaises

class juegos_paises : AppCompatActivity() {

    private lateinit var pregunta: TextView
    private lateinit var bandera: TextView
    private lateinit var opcion1: TextView
    private lateinit var opcion2: TextView
    private lateinit var opcion3: TextView
    private lateinit var opcion4: TextView

    private lateinit var tvAciertos: TextView
    private lateinit var tvMejorPuntuacion: TextView
    private lateinit var paises: List<cPaises>

    private lateinit var currPregunta: cPaises
    private var opciones: MutableList<String> = mutableListOf()
    private var aciertos = 0
    private var mejorPuntuacion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juegos_paises)

        pregunta = findViewById(R.id.tvNombreJuego)
        bandera = findViewById(R.id.tvBandera)
        opcion1 = findViewById(R.id.tvOpcio1)
        opcion2 = findViewById(R.id.tvOpcio2)
        opcion3 = findViewById(R.id.tvOpcio3)
        opcion4 = findViewById(R.id.tvOpcio4)
        tvAciertos = findViewById(R.id.tvAciertos)
        tvMejorPuntuacion = findViewById(R.id.tvMejorPuntuacion)

        val toolbar: Toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)
        toolbar.title = "Juego Paises"
        val colorBlanco = ContextCompat.getColor(this, R.color.black)
        toolbar.setTitleTextColor(colorBlanco)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAciertos.text = aciertos.toString()
        tvMejorPuntuacion.text = mejorPuntuacion.toString()

        loadJson()
        jugarJuego()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadJson() {
        val json: String = this.assets.open("paises.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val paisesData: Paises = gson.fromJson(json, Paises::class.java)
        paises = paisesData.countries
    }

    private fun jugarJuego() {
        currPregunta = paises.random()
        opciones.clear()
        opciones.add(currPregunta.capital_es)
        while (opciones.size < 4) {
            val opcion = paises.random().capital_es
            if (!opciones.contains(opcion)) {
                opciones.add(opcion)
            }
        }

        opciones.shuffle()
        pregunta.text = currPregunta.name_es
        bandera.text = currPregunta.emoji
        opcion1.text = opciones[0]
        opcion2.text = opciones[1]
        opcion3.text = opciones[2]
        opcion4.text = opciones[3]

        opcion1.setOnClickListener {
            verificarRespuesta(opcion1.text.toString())
        }

        opcion2.setOnClickListener {
            verificarRespuesta(opcion2.text.toString())
        }

        opcion3.setOnClickListener {
            verificarRespuesta(opcion3.text.toString())
        }

        opcion4.setOnClickListener {
            verificarRespuesta(opcion4.text.toString())
        }
    }

    private fun verificarRespuesta(respuesta: String) {
        if (respuesta == currPregunta.capital_es) {

            aciertos++
            Snackbar.make(findViewById(R.id.main), "¡Correcto!", Snackbar.LENGTH_SHORT).show()
            jugarJuego()

        } else {

            if (aciertos > mejorPuntuacion) {
                mejorPuntuacion = aciertos
                tvMejorPuntuacion.text = mejorPuntuacion.toString()
            }

            aciertos = 0
            Snackbar.make(findViewById(R.id.main), "Incorrecto. La respuesta correcta es: " +
                    "${currPregunta.capital_es}", Snackbar.LENGTH_LONG)

                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        finish() // Vuelve a la actividad principal
                    }
                }).show()
        }

        tvAciertos.text = aciertos.toString()
    }
}
