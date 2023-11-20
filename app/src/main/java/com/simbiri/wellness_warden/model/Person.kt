package com.simbiri.wellness_warden.model

import android.os.Parcel
import android.os.Parcelable

data class Nutrient(
    var rdi: Double,
    var consumed: Double = 0.0,
    var unit: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(rdi)
        parcel.writeDouble(consumed)
        parcel.writeString(unit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Nutrient> {
        override fun createFromParcel(parcel: Parcel): Nutrient {
            return Nutrient(parcel)
        }

        override fun newArray(size: Int): Array<Nutrient?> {
            return arrayOfNulls(size)
        }
    }
}

enum class Gender {
    MALE,
    FEMALE
}

data class Person(
    var age: Int = 20,
    var gender: Gender = Gender.MALE,
    var weight: Double = 170.5,
    var height: Double = 70.0,
    var bmi: Double = 0.0,
    var caloricIntake: Double = 0.0,
    var bmr: Double = 1200.0,
    val nutrients: Map<String, Nutrient> = baseNutrients()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        Gender.valueOf(parcel.readString() ?: "MALE"),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        mutableMapOf<String, Nutrient>().apply {
            val size = parcel.readInt()
            for (i in 0 until size) {
                put(parcel.readString() ?: "", parcel.readParcelable(Nutrient::class.java.classLoader)!!)
            }
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(age)
        parcel.writeString(gender.name)
        parcel.writeDouble(weight)
        parcel.writeDouble(height)
        parcel.writeDouble(bmi)
        parcel.writeDouble(caloricIntake)
        parcel.writeDouble(bmr)
        parcel.writeInt(nutrients.size)
        for ((key, value) in nutrients) {
            parcel.writeString(key)
            parcel.writeParcelable(value, flags)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }

        fun baseNutrients() = mapOf(
            "protein" to Nutrient(rdi = 50.0, unit = "g"),
            "carbs" to Nutrient(rdi = 130.0, unit = "g"),
            "fats" to Nutrient(rdi = 100.0, unit = "g"),
            "vitaminA" to Nutrient(rdi = 100.0, unit = "μg"),
            "vitaminD" to Nutrient(rdi = 100.0, unit = "μg"),
            "sugars" to Nutrient(rdi = 100.0, unit = "g"),
            "iron" to Nutrient(rdi = 100.0, unit = "mg"),
            "potassium" to Nutrient(rdi = 100.0, unit = "mg"),
            "magnesium" to Nutrient(rdi = 100.0, unit = "mg")
        )
    }

    fun consume(foodItem: FoodItem) {
        caloricIntake += foodItem.macroNutrients.calories
        nutrients["protein"]?.consumed = (nutrients["protein"]?.consumed ?: 0.0) + foodItem.macroNutrients.protein
        nutrients["carbs"]?.consumed = (nutrients["carbs"]?.consumed ?: 0.0) + foodItem.macroNutrients.carbs
        nutrients["fats"]?.consumed = (nutrients["fats"]?.consumed ?: 0.0) + foodItem.macroNutrients.fats
        nutrients["vitaminA"]?.consumed = (nutrients["vitaminA"]?.consumed ?: 0.0) + foodItem.microNutrients.vitaminA
        nutrients["vitaminD"]?.consumed = (nutrients["vitaminD"]?.consumed ?: 0.0) + foodItem.microNutrients.vitaminD
        nutrients["sugars"]?.consumed = (nutrients["sugars"]?.consumed ?: 0.0) + foodItem.microNutrients.sugars
        nutrients["iron"]?.consumed = (nutrients["iron"]?.consumed ?: 0.0) + foodItem.microNutrients.iron
        nutrients["potassium"]?.consumed = (nutrients["potassium"]?.consumed ?: 0.0) + foodItem.microNutrients.potassium
        nutrients["magnesium"]?.consumed = (nutrients["magnesium"]?.consumed ?: 0.0) + foodItem.microNutrients.magnesium
    }
}
