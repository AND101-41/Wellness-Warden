package com.simbiri.wellness_warden.model

import android.os.Parcel
import android.os.Parcelable

data class MicroNutrients(
    val vitaminA: Double,
    val vitaminD: Double,
    val sugars: Double,
    val iron: Double,
    val calcium: Double,
    val fiber: Double,
    val potassium: Double,
    val magnesium: Double
    // Include other nutrients as needed

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(vitaminA)
        parcel.writeDouble(vitaminD)
        parcel.writeDouble(sugars)
        parcel.writeDouble(iron)
        parcel.writeDouble(calcium)
        parcel.writeDouble(sugars)
        parcel.writeDouble(potassium)
        parcel.writeDouble(magnesium)
        parcel.writeDouble(fiber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MicroNutrients> {
        override fun createFromParcel(parcel: Parcel): MicroNutrients {
            return MicroNutrients(parcel)
        }

        override fun newArray(size: Int): Array<MicroNutrients?> {
            return arrayOfNulls(size)
        }
    }

    fun addInstances(other: MicroNutrients): MicroNutrients {
        return MicroNutrients(
            this.vitaminA + other.vitaminA,
            this.vitaminD + other.vitaminD,
            this.sugars + other.sugars,
            this.iron + other.iron,
            this.calcium + other.calcium,
            this.fiber + other.fiber,
            this.potassium + other.potassium,
            this.magnesium + other.magnesium
        )
    }

}


