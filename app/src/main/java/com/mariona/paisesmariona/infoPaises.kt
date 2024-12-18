package com.mariona.paisesmariona

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.mariona.paisesmariona.adapter.paisesAdapter
import java.util.Locale

class infoPaises : AppCompatActivity() {

    lateinit var adapter: paisesAdapter
    private var ingles = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_paises)

        val nombrePais = intent.getStringExtra("nombrePais")
        val capitalPais = intent.getStringExtra("capitalPais")
        val continentePais = intent.getStringExtra("continentePais")
        val kmPais = intent.getDoubleExtra("kmPais", 0.0)
        val codigoPais = intent.getStringExtra("dial_code")
        val emoji = intent.getStringExtra("emoji")
        val codigo2Pais = intent.getStringExtra("code2")
        val codigo3Pais = intent.getStringExtra("code3")
        val tldPais = intent.getStringExtra("tld")
        var estrella = intent.getBooleanExtra("favoritos", false)

        findViewById<TextView>(R.id.tvNombre).text = nombrePais
        findViewById<TextView>(R.id.tvCapital).text = capitalPais
        findViewById<TextView>(R.id.tvContinente).text = continentePais
        findViewById<TextView>(R.id.tvKm).text = kmPais.toString()
        findViewById<TextView>(R.id.tvCode).text = codigoPais
        findViewById<TextView>(R.id.tvCode2).text = codigo2Pais
        findViewById<TextView>(R.id.tvCode3).text = codigo3Pais
        findViewById<TextView>(R.id.tvTLD).text = tldPais
        findViewById<TextView>(R.id.tvEmoji).text = emoji

        val imgEstrella = findViewById<ImageView>(R.id.imageViewEstrella)

        try {
            if (estrella) {
                imgEstrella.setImageResource(R.drawable.baseline_star_24)
            } else {
                imgEstrella.setImageResource(R.drawable.baseline_star_outline_24)
            }
        } catch (e: Exception) {
            imgEstrella.setImageResource(R.drawable.baseline_star_outline_24)
        }

        imgEstrella.setOnClickListener {
            estrella = !estrella
            if (estrella) {
                imgEstrella.setImageResource(R.drawable.baseline_star_24)

            } else {
                imgEstrella.setImageResource(R.drawable.baseline_star_outline_24)
            }
            adapter.notifyDataSetChanged()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar1)
        toolbar.title = nombrePais
        val colorBlanco = ContextCompat.getColor(this, R.color.white)
        toolbar.setTitleTextColor(colorBlanco)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val btnEspanol = findViewById<Button>(R.id.btnEspanol)
        val btnIngles = findViewById<Button>(R.id.btnIngles)

        val currentLocale = Locale.getDefault().language
        if (currentLocale == "es") {
            ingles = false
            btnEspanol.setBackgroundColor(ContextCompat.getColor(this, R.color.press))
            btnIngles.setBackgroundColor(ContextCompat.getColor(this, R.color.button))
        } else {
            ingles = true
            btnEspanol.setBackgroundColor(ContextCompat.getColor(this, R.color.button))
            btnIngles.setBackgroundColor(ContextCompat.getColor(this, R.color.press))
        }

        btnEspanol.setOnClickListener {
            ingles = false
            btnIngles.setBackgroundColor(ContextCompat.getColor(this, R.color.button))
            btnEspanol.setBackgroundColor(ContextCompat.getColor(this, R.color.press))
            mostrarInformacion(
                nombrePais, capitalPais, continentePais, kmPais, emoji, codigoPais,
                codigo2Pais, codigo3Pais, tldPais, estrella
            )
        }

        btnIngles.setOnClickListener {
            ingles = true
            btnEspanol.setBackgroundColor(ContextCompat.getColor(this, R.color.button))
            btnIngles.setBackgroundColor(ContextCompat.getColor(this, R.color.press))
            mostrarInformacion(
                nombrePais, capitalPais, continentePais, kmPais, emoji, codigoPais,
                codigo2Pais, codigo3Pais, tldPais, estrella
            )
        }

        mostrarInformacion(
            nombrePais, capitalPais, continentePais, kmPais, emoji, codigoPais,
            codigo2Pais, codigo3Pais, tldPais, estrella
        )
    }

    private fun mostrarInformacion(
        nombrePais: String?, capitalPais: String?, continentePais: String?,
        kmPais: Double?, emoji: String?, codigoPais: String?,
        codigo2Pais: String?, codigo3Pais: String?, tldPais: String?, favoritos: Boolean
    ) {
        val tvDetalleNombre: TextView = findViewById(R.id.tvNombre)
        val tvDetalleCapital: TextView = findViewById(R.id.tvCapital)
        val tvDetalleContinente: TextView = findViewById(R.id.tvContinente)
        val tvDetalleKm: TextView = findViewById(R.id.tvKm)
        val tvDetalleCode: TextView = findViewById(R.id.tvCode)
        val tvDetalleCode2: TextView = findViewById(R.id.tvCode2)
        val tvDetalleCode3: TextView = findViewById(R.id.tvCode3)
        val tvDetalleTLD: TextView = findViewById(R.id.tvTLD)
        val tvEmoji: TextView = findViewById(R.id.tvEmoji)
        val imgEstrella: ImageView = findViewById(R.id.imageViewEstrella)

        tvEmoji.text = emoji
        if (ingles) {
            tvDetalleNombre.text = "Name: $nombrePais"
            tvDetalleCapital.text = "Capital: $capitalPais"
            tvDetalleContinente.text = "Continent: $continentePais"
            tvDetalleKm.text = "Km2: $kmPais"
            tvDetalleCode.text = "Code: $codigoPais"
            tvDetalleCode2.text = "Code 2: $codigo2Pais"
            tvDetalleCode3.text = "Code 3: $codigo3Pais"
            tvDetalleTLD.text = "TLD: $tldPais"
        } else {
            tvDetalleNombre.text = "Nombre: $nombrePais"
            tvDetalleCapital.text = "Capital: $capitalPais"
            tvDetalleContinente.text = "Continente: $continentePais"
            tvDetalleKm.text = "Km2: $kmPais"
            tvDetalleCode.text = "Código: $codigoPais"
            tvDetalleCode2.text = "Código 2: $codigo2Pais"
            tvDetalleCode3.text = "Código 3: $codigo3Pais"
            tvDetalleTLD.text = "TLD: $tldPais"
        }

        tvEmoji.setOnClickListener {
            abrirWikipedia(this, nombrePais)
        }
    }

    private fun abrirWikipedia(context: Context, nombrePais: String?) {
        val urlWikipedia = "https://es.wikipedia.org/wiki/$nombrePais"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWikipedia))

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir el enlace", Toast.LENGTH_SHORT).show()
        }
    }
}
