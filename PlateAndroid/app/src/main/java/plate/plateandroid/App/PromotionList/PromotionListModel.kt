package plate.plateandroid.App.PromotionList

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionListModel: Comparator<PromotionModel> {
    var promotions = mutableMapOf<PromotionModel, Boolean>()

    fun changeButton(promotionModel: PromotionModel) {
        promotions[promotionModel] = false
    }

    fun removeCell(promotionModel: PromotionModel) {
        promotions.remove(promotionModel)
    }

    override fun compare(promotionModelLeft: PromotionModel, promotionModelRight: PromotionModel): Int {
        if(promotionModelLeft.start_time > promotionModelRight.start_time) {
            return 1
        }else {
            return -1
        }
    }
}