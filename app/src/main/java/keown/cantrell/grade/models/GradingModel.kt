package keown.cantrell.grade.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GradingModel(
        var id: Long = 0,
        var title: String = "",
        var description: String = "",
        var Grade: String = "",
        var Other: String = "",
        var image: String = ""
) : Parcelable

