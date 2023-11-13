package com.simbiri.wellness_warden.model
import android.os.Parcel
import android.os.Parcelable

data class MacroNutrients(
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fats: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(calories)
        parcel.writeDouble(protein)
        parcel.writeDouble(carbs)
        parcel.writeDouble(fats)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MacroNutrients> {
        override fun createFromParcel(parcel: Parcel): MacroNutrients {
            return MacroNutrients(parcel)
        }

        override fun newArray(size: Int): Array<MacroNutrients?> {
            return arrayOfNulls(size)
        }
    }

    fun addInstances(other: MacroNutrients): MacroNutrients {
        return MacroNutrients(
            this.calories + other.calories,
            this.protein + other.protein,
            this.carbs + other.carbs,
            this.fats + other.fats
        )
    }

}



