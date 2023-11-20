package com.simbiri.wellness_warden

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simbiri.wellness_warden.model.Nutrient

class NutrientListAdapter(val context: Context, private val nutrientMap: Map<String, Nutrient>) :
    RecyclerView.Adapter<NutrientListAdapter.NutrientViewHolder>() {

    inner class NutrientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nutrientNameTextView = itemView.findViewById<TextView>(R.id.nutrientName)
        private var nutrientValueTextView = itemView.findViewById<TextView>(R.id.nutrientInfo)
        private var nutrientProgressBar = itemView.findViewById<ProgressBar>(R.id.nutrientTrack)

        fun setDataToItem(nutrientName: String, nutrient: Nutrient) {
            nutrientNameTextView.text = nutrientName
            nutrientValueTextView.text =
                context.getString(
                    R.string.consumed_recommended,
                    String.format("%.2f", nutrient.consumed),
                    nutrient.unit,
                    String.format("%.2f", nutrient.rdi),
                    nutrient.unit
                )

            nutrientProgressBar.max = nutrient.rdi.toInt()
            nutrientProgressBar.progress = nutrient.consumed.toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nutrient_list_item, parent, false)
        return NutrientViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutrientViewHolder, position: Int) {
        val nutrientEntry = nutrientMap.entries.toList()[position]
        holder.setDataToItem(nutrientEntry.key, nutrientEntry.value)
    }

    override fun getItemCount(): Int {
        return nutrientMap.size
    }
}
