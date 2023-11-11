package com.simbiri.wellness_warden

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

data class Food(val imageId : Int, val foodName : String, val foodInfo : String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(foodName)
        dest.writeString(foodInfo)
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}

object CommonFoods{

    var listImages = arrayOf(
        R.drawable.chicken, R.drawable.beef, R.drawable.milk, R.drawable.mexicantaco
    )

    var listNamesFood = arrayOf(
        "Fried Chicken", "Roasted beef", "Fresh Milk", "Mexican Taco"
    )

    var listFoodInfo = arrayOf(
        "Energy : 250kcals, Protein:  26g,  Carbs : 40g",
        "Energy : 200kcals, Protein :  43g,  Carbs : 10g",
        "Energy : 150kcals, Protein :  54g,  Carbs : 40g",
        "Energy :300kcals, Protein :  7g,  Carbs : 140g"
    )

    var arrayListFoods: ArrayList<Food>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()

            for (imagePosition in listImages.indices) {
                val imageId = listImages[imagePosition]
                val nameOfFood = listNamesFood[imagePosition]
                val infoFood = listFoodInfo[imagePosition]

                val foodInstance = Food(imageId, nameOfFood,infoFood)

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