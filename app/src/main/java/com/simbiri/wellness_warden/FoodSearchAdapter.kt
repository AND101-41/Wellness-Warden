package com.simbiri.wellness_warden

import com.simbiri.wellness_warden.model.FoodItem

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


object CommonFoods {
    var allBreakFast = ArrayList<FoodItem>()
    var allLunch = ArrayList<FoodItem>()
    var allDinner = ArrayList<FoodItem>()
    var allSnacks = ArrayList<FoodItem>()
    var allFoods = ArrayList<FoodItem>()

}

class FoodSearchAdapter(var context: Context, var arrayFoodList: ArrayList<FoodItem>) :
    RecyclerView.Adapter<FoodSearchAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var positionItem = 1
        private var currentFood: FoodItem? = null

        private var nameFoodTextView: TextView = itemView.findViewById(R.id.nameOfFood)


        fun setDatatoItem(foodInstance: FoodItem, position: Int) {

            this.positionItem = position
            this.currentFood = foodInstance

            nameFoodTextView.text = currentFood!!.name

        }

        fun setOnClickListeners() {
            itemView.setOnClickListener(this@FoodViewHolder)
        }

        override fun onClick(v: View?) {

            val bottomSheetSearchFragment = BottomSheetSearchFragment.newInstance(currentFood!!)
            val transaction =
                (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            bottomSheetSearchFragment.show(transaction, bottomSheetSearchFragment.tag)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.search_recycler_item, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(foodViewHolder: FoodViewHolder, position: Int) {
        val foodInstance = arrayFoodList[position]

        foodViewHolder.setOnClickListeners()
        foodViewHolder.setDatatoItem(foodInstance, position)
    }

    override fun getItemCount(): Int {

        return arrayFoodList.size
    }

}