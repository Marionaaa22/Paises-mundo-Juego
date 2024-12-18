package com.mariona.paisesmariona.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mariona.paisesmariona.R
import com.mariona.paisesmariona.classes.cPaises
import com.mariona.paisesmariona.infoPaises

class paisesAdapter(private val listaOriginal: MutableList<cPaises>, private val context: Context) : RecyclerView.Adapter<paisesAdapter.PaisViewHolder>() {

    var paises: MutableList<cPaises> = listaOriginal

    inner class PaisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)
        val tvContinente: TextView = itemView.findViewById(R.id.tvContinente)
        val tvKm: TextView = itemView.findViewById(R.id.tvKm)
        val favorito: ImageView = itemView.findViewById(R.id.imageViewEstrella)
        val tvEmoji: TextView = itemView.findViewById(R.id.tvEmoji)

        fun bind(pais: cPaises) {
            tvNombre.text = "Nombre: ${pais.name_es}"
            tvCapital.text = "Capital: ${pais.capital_es}"
            tvContinente.text = "Continente: ${pais.continent_es}"
            tvKm.text = "Km2: ${pais.km2}"

            val kmText = if (pais.km2 > 1000000) {
                "<b>Km2: ${pais.km2}</b>"
            } else {
                "Km2: ${pais.km2}"
            }
            tvKm.text = Html.fromHtml(kmText, Html.FROM_HTML_MODE_COMPACT)
            tvEmoji.text = pais.emoji

            cardView.setOnClickListener {

                val intent = Intent(context, infoPaises::class.java)
                intent.putExtra("nombrePais", pais.name_es)
                intent.putExtra("capitalPais", pais.capital_es)
                intent.putExtra("continentePais", pais.continent_en)
                intent.putExtra("kmPais", pais.km2)
                intent.putExtra("dial_code", pais.dial_code)
                intent.putExtra("code2", pais.code_2)
                intent.putExtra("code3", pais.code_3)
                intent.putExtra("tld", pais.tld)
                intent.putExtra("emoji", pais.emoji)
                intent.putExtra("favoritos", pais.favoritos)

                context.startActivity(intent)
            }

            tvEmoji.setOnClickListener {
                abrirWikipedia(tvEmoji.context, pais.name_es)
            }

            val colorResId = when (pais.continent_es) {
                "América del Norte" -> R.color.cAmérica_del_Norte
                "América del Sur" -> R.color.cAmérica_del_Sur
                "Antártida" -> R.color.cAntártida
                "Europa" -> R.color.cEuropa
                "Oceanía" -> R.color.cOceania
                "Asia" -> R.color.cAsia
                "África" -> R.color.cÁfrica
                else -> R.color.white
            }
            cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, colorResId))

            favorito.setOnClickListener {
                pais.favoritos = !pais.favoritos
                notifyItemChanged(adapterPosition)
            }

            favorito.setImageResource(
                if (pais.favoritos)
                    R.drawable.baseline_star_24
                else
                    R.drawable.baseline_star_outline_24)
        }

        private fun abrirWikipedia(context: Context, nombrePais: String) {
            val urlWikipedia = "https://es.wikipedia.org/wiki/$nombrePais"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWikipedia))

            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al abrir el enlace", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_paises, parent, false)
        return PaisViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val pais = paises[position]
        holder.bind(pais)
    }

    override fun getItemCount(): Int {
        return paises.size
    }
}