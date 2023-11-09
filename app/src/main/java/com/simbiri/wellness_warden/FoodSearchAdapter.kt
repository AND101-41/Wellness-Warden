package com.simbiri.wellness_warden

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

data class Food(val imageId : Int, val foodName : String)

object CommonFoods{

    var listImages =  arrayOf(
        R.drawable.chicken, R.drawable.beef,R.drawable.milk,R.drawable.mexicantaco
    )

    var listNamesFood =  arrayOf(
        "Fried Chicken", "Roasted beef", "Glass of Milk", "Mexican Taco"
    )

    var arrayListFoods : ArrayList<Food>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()

            for (imagePosition in listImages.indices) {
                val imageId = listImages[imagePosition]
                val nameOfFood = listNamesFood[imagePosition]

                val foodInstance = Food(imageId, nameOfFood)

                field!!.add(foodInstance)
            }

            return field

        }

    var allBreakFast = ArrayList<Food>()
    var allLunch = ArrayList<Food>()
    var allDinner = ArrayList<Food>()
    var allSnacks = ArrayList<Food>()

}

class FoodSearchAdapter (var context: Context, var arrayFoodList : ArrayList<Food>) :
    RecyclerView.Adapter<FoodSearchAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var positionItem = 1
        private var currentFood: Food? = null

        private var foodImageView: ImageView = itemView.findViewById(R.id.imageOfFood)
        private var nameFoodTextView: TextView = itemView.findViewById(R.id.nameOfFood)


        fun setDatatoItem(foodInstance: Food, position: Int) {

            this.positionItem = position
            this.currentFood = foodInstance

            foodImageView.setImageResource(currentFood!!.imageId)
            nameFoodTextView.text = currentFood!!.foodName

        }

        fun setOnClickListeners(){
            itemView.setOnClickListener(this@FoodViewHolder)
        }

        override fun onClick(v: View?) {

            CommonFoods.allBreakFast.add(currentFood!!)
            CommonFoods.allDinner.add(currentFood!!)
            CommonFoods.allLunch.add(currentFood!!)
            CommonFoods.allSnacks.add(currentFood!!)

            Toast.makeText(context, "Added Food Item to BreakFast/Lunch/Dinner/Snack list", Toast.LENGTH_LONG).show()

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