package plate.plateandroid.App.PromotionList

import plate.plateandroid.App.PromotionList.Complements.PromotionAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by rennerll on 11/24/17.
 */
class PromotionListController: PromotionListInterface.Controller {
    companion object {
        val GOING_REQUEST = "0"
        val NO_FOOD_REQUEST = "1"
    }

    lateinit var view: PromotionListInterface.View
    lateinit var promotionAPI: PromotionAPI
    lateinit var promotionListModel: PromotionListModel
    lateinit var username: String

    override fun init(view: PromotionListInterface.View, username: String) {
        this.view = view
        view.showLoading()
        this.promotionAPI = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://plate-heroku-database.herokuapp.com/")
                .build()
                .create(PromotionAPI::class.java)
        this.promotionListModel = PromotionListModel()
        this.username = username
    }

    override fun initializePromotionList() {
        val observableWithResponseToGo = promotionAPI.readPromotionsToGo(username)
        observableWithResponseToGo.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { promotionsToGo ->
                            val promotionsToGoMap = promotionsToGo.associateBy({it}, { true })
                            promotionListModel.promotions.putAll(promotionsToGoMap)

                            // see this later
                            val observableWithResponseGoing = promotionAPI.readPromotionsGoing(username)
                            observableWithResponseGoing.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            { promotionsGoing ->
                                                val promotionsGoingMap = promotionsGoing.associateBy({it}, { false })
                                                promotionListModel.promotions.putAll(promotionsGoingMap)
                                                view.setItemsList(promotionListModel.promotions)
                                                view.hideLoading()
                                            },
                                            { error ->
                                                view.showToast("Something went wrong. Please, try again.")
                                                error.printStackTrace()
                                                view.hideLoading()
                                            }
                                    )
                        },
                        { error ->
                            view.showToast("Something went wrong. Please, try again.")
                            error.printStackTrace()
                            view.hideLoading()
                        }
                )
    }

    override fun respondToClick(promotionModel: PromotionModel, firstClick: Boolean) {
        if(firstClick) {
            val observableWithResponseToGo = promotionAPI.createRequest(username, promotionModel.promotion_id, GOING_REQUEST)
            observableWithResponseToGo.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { success ->
                                if(success) {
                                    promotionListModel.changeButton(promotionModel)
                                    view.showToast("Enjoy!")
                                    view.setItemsList(promotionListModel.promotions) // TODO: better way to do it, reload data??
                                } else {
                                    view.showToast("Sorry. This action in unavailable now.")
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, try again.")
                                error.printStackTrace()
                            }
                    )
        }else {
            view.showNoFoodDialogFragmentFromController(promotionModel)
        }
    }

    override fun handleNoFoodDialog(confirm: Boolean, promotionModel: PromotionModel?) {
        if(confirm && promotionModel != null) {
            val observableWithResponseToGo = promotionAPI.createRequest(username, promotionModel.promotion_id, NO_FOOD_REQUEST)
            observableWithResponseToGo.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { success ->
                                view.dismissNoFoodDialogFragmentFromController()

                                if(success) {
                                    promotionListModel.removeCell(promotionModel)
                                    view.showToast("Thanks for informing!") // TODO: better way to do it, reload data??
                                    view.setItemsList(promotionListModel.promotions)
                                }else {
                                    view.showToast("Sorry. This action in unavailable now.")
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, try again.")
                                error.printStackTrace()
                            }
                    )
        }else {
            view.dismissNoFoodDialogFragmentFromController()
            view.showToast("OK!")
        }
    }

    override fun handleAddPromotionDialog(confirm: Boolean, promotionModel: PromotionModel?) {
        if(confirm && promotionModel != null) {
            val observableWithResponseToGo = promotionAPI.createPromotion(promotionModel.title, promotionModel.start_time, promotionModel.end_time, promotionModel.location)
            observableWithResponseToGo.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { promotionId ->
                                view.dismissAddPromotionDialogFragmentFromController()
//                                val promotionId = response.body()

                                if(promotionId != "") {
                                    promotionModel.promotion_id = promotionId as String
                                    promotionListModel.promotions.put(promotionModel, true)
                                    view.showToast("Thanks for adding!")
                                    view.setItemsList(promotionListModel.promotions) // TODO: better way to do it, reload data??
                                }else {
                                    view.showToast("Sorry. This action in unavailable now.") // see this
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, try again.")
                                error.printStackTrace()
                            }
                    )
        }else {
            view.dismissAddPromotionDialogFragmentFromController()
            view.showToast("OK!")
        }
    }

}