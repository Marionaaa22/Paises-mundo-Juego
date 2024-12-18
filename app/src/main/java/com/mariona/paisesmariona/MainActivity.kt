package com.mariona.paisesmariona

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mariona.paisesmariona.classes.cPaises
import com.mariona.paisesmariona.classes.Paises
import com.google.gson.Gson
import com.mariona.paisesmariona.adapter.paisesAdapter

class MainActivity : AppCompatActivity() {

    lateinit var adapter: paisesAdapter
    lateinit var datos: RecyclerView
    var listaOriginal: ArrayList<cPaises> = ArrayList()
    var listaFiltrada: ArrayList<cPaises> = ArrayList()

    var filtroActual: String? = null
    var ordenActual: String? = null
    var mostrarFavoritos: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar1))
        val toolbar: Toolbar = findViewById(R.id.toolbar1)
        toolbar.title = "Países"

        val colorBlanco = ContextCompat.getColor(this, R.color.black)
        toolbar.setTitleTextColor(colorBlanco)
        setSupportActionBar(toolbar)

        biblioteca()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_paises, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mJugarBtn -> {
                val intent = Intent(this, juegos_paises::class.java)
                startActivity(intent)
                return true
            }
            R.id.favPaisesBtn -> {
                mostrarFavoritos = true
                aplicarFiltors()
                return true
            }
            R.id.favTodosBtn, R.id.mostrarTodosBtn -> {
                mostrarFavoritos = false
                filtroActual = null
                aplicarFiltors()
                return true
            }
            R.id.paisesAZBtn -> {
                ordenActual = "paisesAZ"
                aplicarFiltors()
                return true
            }
            R.id.ordenarBtn -> {
                return true
            }
            R.id.paisesZABtn -> {
                ordenActual = "paisesZA"
                aplicarFiltors()
                return true
            }
            R.id.capitalesAZBtn -> {
                ordenActual = "capitalesAZ"
                aplicarFiltors()
                return true
            }
            R.id.capitalesZABtn -> {
                ordenActual = "capitalesZA"
                aplicarFiltors()
                return true
            }
            R.id.africaBtn -> {
                filtroActual = "África"
                aplicarFiltors()
                return true
            }
            R.id.antartidaBtn -> {
                filtroActual = "Antártida"
                aplicarFiltors()
                return true
            }
            R.id.asiaBtn -> {
                filtroActual = "Asia"
                aplicarFiltors()
                return true
            }
            R.id.europaBtn -> {
                filtroActual = "Europa"
                aplicarFiltors()
                return true
            }
            R.id.oceaniaBtn -> {
                filtroActual = "Oceanía"
                aplicarFiltors()
                return true
            }
            R.id.norteAmericaBtn -> {
                filtroActual = "América del Norte"
                aplicarFiltors()
                return true
            }
            R.id.surAmericBtn -> {
                filtroActual = "América del Sur"
                aplicarFiltors()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun biblioteca() {
        datos = findViewById(R.id.rvDatos) as RecyclerView
        datos.setHasFixedSize(true)
        datos.layoutManager = LinearLayoutManager(this)

        loadJson()
        listaFiltrada = ArrayList(listaOriginal) // Inicialmente muestra todos los datos
        adapter = paisesAdapter(listaFiltrada, this)
        datos.adapter = adapter
    }

    private fun aplicarFiltors() {
        var listado = ArrayList(listaOriginal)

        if (mostrarFavoritos) {
            listado = listado.filter { it.favoritos } as ArrayList<cPaises>
        }

        when (ordenActual) {
            "paisesAZ" -> listado.sortBy { it.name_es }
            "paisesZA" -> listado.sortByDescending { it.name_es }
            "capitalesAZ" -> listado.sortBy { it.capital_en }
            "capitalesZA" -> listado.sortByDescending { it.capital_en }
        }

        if (filtroActual != null) {
            listado = listado.filter { it.continent_es == filtroActual } as ArrayList<cPaises>
        }

        listaFiltrada = listado
        adapter.paises = listaFiltrada
        adapter.notifyDataSetChanged()

    }

    private fun loadJson() {
        val json: String = this.assets.open("paises.json").bufferedReader().use { it.readText() }
        val gson: Gson = Gson()
        val paises: Paises = gson.fromJson(json, Paises::class.java)

        paises.countries.forEach {
            listaOriginal.add(cPaises(it.name_en, it.name_es, it.continent_en, it.continent_es, it.capital_en, it.capital_es, it.dial_code, it.code_2, it.code_3, it.tld, it.km2, it.emoji))
        }
    }
}