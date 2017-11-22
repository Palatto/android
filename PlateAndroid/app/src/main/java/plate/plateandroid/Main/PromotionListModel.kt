package plate.plateandroid.Main

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionListModel {
    var promotions = mutableListOf<PromotionModel>()

    fun quantityOfItems(): Int {
        return promotions.size
    }
}