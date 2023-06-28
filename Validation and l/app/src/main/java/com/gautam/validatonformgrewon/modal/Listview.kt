package com.gautam.validatonformgrewon.modal

import android.os.Parcel
import android.os.Parcelable

data class Listview(
    var id: Int,
    var image: String,
    var ratting: Double,
    var description: String,
    var title: String

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeDouble(ratting)
        parcel.writeString(description)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Listview> {
        override fun createFromParcel(parcel: Parcel): Listview {
            return Listview(parcel)
        }

        override fun newArray(size: Int): Array<Listview?> {
            return arrayOfNulls(size)
        }
    }

}
