package com.example.food_app.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.FileDescriptor

@Entity(tableName = "food")
@Parcelize
data class Food(
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String,

    @SerializedName("descriptions")
    @ColumnInfo(name = "description")
    var descriptor: String,

    @SerializedName("currency")
    @ColumnInfo(name = "currency")
    var currency: String,

    @SerializedName("rate")
    @ColumnInfo(name = "rate")
    var rate: String,

    @SerializedName("quantity")
    @ColumnInfo(name = "quantity")
    var quantity: Int?
):Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0

    constructor(): this(title="", descriptor="", currency="", rate="", quantity= 0)
}
