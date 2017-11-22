package plate.plateandroid.Main

import com.google.gson.Gson

/**
 * Created by rennerll on 10/20/17.
 */
class MainPresenter: MainContract.Presenter {
    lateinit var view: MainContract.View
    var promotionList: PromotionListModel = PromotionListModel()

    override fun init(view: MainContract.View) {
        this.view = view
    }

    override fun initializePromotionList() {
        // Learn retrofit later, or a better solution to make requests
        GetPromotionsTask(object: LoadListInterface {
            override fun tryToLoad(result: String) {
                val success = this@MainPresenter.trySerialization(result)
                if(success) this@MainPresenter.view.setItemsList(promotionList.promotions)
            }
        }).execute("https://plate-heroku-database.herokuapp.com/promotions")
    }

    private fun trySerialization(result: String): Boolean {
        try {
            val gson = Gson()
            val promotions = gson.fromJson(result, Array<PromotionModel>::class.java)
            promotionList.promotions = promotions.toMutableList()
            return true
        }catch(error: Throwable) {
            error.printStackTrace()
        }
        return false
    }
}