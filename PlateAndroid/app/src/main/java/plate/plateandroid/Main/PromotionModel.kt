package plate.plateandroid.Main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionModel: Serializable {
    @SerializedName("promotion_id") var promotion_id: String = ""
    @SerializedName("title") var title: String = ""
    @SerializedName("start_time") var start_time: String = ""
    @SerializedName("end_time") var end_time: String = ""
    @SerializedName("location") var location: String = ""

    // See if this constructor is necessary
    constructor(promotion_id: String, title: String, start_time: String, end_time: String, location: String) {
        this.promotion_id = promotion_id
        this.title = title
        this.start_time = start_time
        this.end_time = end_time
        this.location = location
    }

    fun getTime(): String {
        return "Start: " +  this.start_time + " End: " + this.end_time
    }
}