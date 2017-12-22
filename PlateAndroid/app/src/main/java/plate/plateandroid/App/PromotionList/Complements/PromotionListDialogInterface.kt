package plate.plateandroid.App.PromotionList.Complements

import plate.plateandroid.App.PromotionList.PromotionModel

/**
 * Created by rennerll on 12/13/17.
 */
interface PromotionListDialogInterface {
    fun showNoFoodDialogFragment(promotionModel: PromotionModel)
    fun dismissNoFoodDialogFragment(confirm: Boolean, promotionModel: PromotionModel?)
    fun showAddPromotionDialogFragment()
    fun dismissAddPromotionDialogFragment(confirm: Boolean, promotionModel: PromotionModel?)
}