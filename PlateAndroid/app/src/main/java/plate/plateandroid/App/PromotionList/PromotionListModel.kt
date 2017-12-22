package plate.plateandroid.App.PromotionList

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionListModel: Comparator<PromotionModel> {
    val promotions = mutableMapOf<PromotionModel, Boolean>()

    fun quantityOfItems(): Int {
        return promotions.size
    }

    fun changeButton(promotionModel: PromotionModel) {
        promotions[promotionModel] = false
    }

    fun removeCell(promotionModel: PromotionModel) {
        promotions.remove(promotionModel)
    }

    // For sorting
    override fun compare(promotionModelLeft: PromotionModel, promotionModelRight: PromotionModel): Int {
        if(promotionModelLeft.start_time > promotionModelRight.start_time) {
            return 1
        }else {
            return -1
        }
    }
}