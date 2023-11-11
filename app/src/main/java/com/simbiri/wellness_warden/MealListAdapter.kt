package com.simbiri.wellness_warden

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MealListAdapter(val context: Context, val mealArrayList: ArrayList<Food>) :
    RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var currentFood: Food? = null
        private var positionItem = 1

        private var nameFoodTextView = itemView.findViewById<TextView>(R.id.nameOfFoodItem)
        private var imageOfFood = itemView.findViewById<ImageView>(R.id.imageFoodInMeal)
        private var textAdd = itemView.findViewById<TextView>(R.id.addQuantityText)
        private var textNumberUnits = itemView.findViewById<TextView>(R.id.textQuantityMeals)
        private var textInfoFood = itemView.findViewById<TextView>(R.id.textInfoNutrientsFood)


        fun setDataToItem(foodInstance: Food, position: Int) {

            this.positionItem = position
            this.currentFood = foodInstance

            imageOfFood.setImageResource(currentFood!!.imageId)
            nameFoodTextView.text = currentFood!!.foodName

        }

        fun setOnClickListeners() {
            textAdd.setOnClickListener {
                var numberFood = textNumberUnits.text.toString().toInt()
                numberFood += 1
                textNumberUnits.text = numberFood.toString()
            }

            textInfoFood.setOnClickListener {
                val bottomSheetMealFragment = BottomSheetMealFragment.newInstance(currentFood!!)
                val transaction =
                    (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                bottomSheetMealFragment.show(transaction, bottomSheetMealFragment.tag)

            }

            imageOfFood.setOnClickListener {
                val bottomSheetMealFragment = BottomSheetMealFragment.newInstance(currentFood!!)
                val transaction =
                    (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                bottomSheetMealFragment.show(transaction, bottomSheetMealFragment.tag)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.meal_list_item, parent, false)

        return MealViewHolder(view)
    }

    override fun onBindViewHolder(mealViewolder: MealViewHolder, position: Int) {
        mealViewolder.setDataToItem(mealArrayList[position], position)
        mealViewolder.setOnClickListeners()

    }

    override fun getItemCount(): Int {
        return mealArrayList.size
    }
}
