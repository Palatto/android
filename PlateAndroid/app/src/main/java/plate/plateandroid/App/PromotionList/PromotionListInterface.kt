package plate.plateandroid.App.PromotionList

/**
 * Created by rennerll on 11/24/17.
 */
interface PromotionListInterface {
    interface View {
        fun showToast(message: String)
        fun setItemsList(promotions: MutableMap<PromotionModel, Boolean>)
        fun showNoFoodDialogFragmentFromController(promotionModel: PromotionModel)
        fun dismissNoFoodDialogFragmentFromController()
        fun dismissAddPromotionDialogFragmentFromController()
        fun showLoading()
        fun hideLoading()
        fun stopRefreshing()
    }

    interface Controller {
        fun init(view : View, username: String)
        fun initializePromotionList()
        fun refreshPromotionList()
        fun respondToClick(promotionModel: PromotionModel, firstClick: Boolean)
        fun handleNoFoodDialog(confirm: Boolean, promotionModel: PromotionModel?)
        fun handleAddPromotionDialog(confirm: Boolean, promotionModel: PromotionModel?)
    }
}