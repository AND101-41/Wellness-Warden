package com.simbiri.wellness_warden.model
import android.os.Parcel
import android.os.Parcelable


data class FoodItem(
    val id: String,
    val name: String,
    val info: String,
    var quantity: Double,
    val macroNutrients: MacroNutrients,
    val microNutrients: MicroNutrients,
    var imageId: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readParcelable(MacroNutrients::class.java.classLoader)!!,
        parcel.readParcelable(MicroNutrients::class.java.classLoader)!!,
        parcel.readString() ?: ""

    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(info)
        dest.writeDouble(quantity)
        dest.writeParcelable(macroNutrients, flags)
        dest.writeParcelable(microNutrients, flags)
        dest.writeString(imageId)
    }

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem {
            return FoodItem(parcel)
        }

        override fun newArray(size: Int): Array<FoodItem?> {
            return arrayOfNulls(size)
        }
    }
}


