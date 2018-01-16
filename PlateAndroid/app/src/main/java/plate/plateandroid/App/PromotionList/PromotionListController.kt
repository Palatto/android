package plate.plateandroid.App.PromotionList

import plate.plateandroid.App.PromotionList.Complements.PromotionAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

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
                                                view.showToast("Something went wrong. Please, check your internet connection and try again.")
                                                error.printStackTrace()
                                                view.hideLoading()
                                            }
                                    )
                        },
                        { error ->
                            view.showToast("Something went wrong. Please, check your internet connection and try again.")
                            error.printStackTrace()
                            view.hideLoading()
                        }
                )
    }

    override fun refreshPromotionList() {
        promotionListModel.promotions = mutableMapOf<PromotionModel, Boolean>()
        initializePromotionList()
        view.stopRefreshing()
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
                                    view.showToast("Enjoy!") // TODO: better way to do it, reload data??
                                    view.setItemsList(promotionListModel.promotions)
                                } else {
                                    view.showToast("Sorry. This action in unavailable (event has no more food or is over).")
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, check your internet connection and try again.")
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
                                    view.showToast("Thanks for letting us know!") // TODO: better way to do it, reload data??
                                    view.setItemsList(promotionListModel.promotions)
                                }else {
                                    view.showToast("Sorry. This action in unavailable (event has no more food or is over).")
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, check your internet connection and try again.")
                                error.printStackTrace()
                            }
                    )
        }else {
            view.dismissNoFoodDialogFragmentFromController()
        }
    }

    override fun handleAddPromotionDialog(confirm: Boolean, promotionModel: PromotionModel?) {
        if(confirm && promotionModel != null) {

            // see if more validations are needed
            val myFormat = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(myFormat)
            val start_time_date = sdf.parse(promotionModel.start_time)
            val end_time_date = sdf.parse(promotionModel.end_time)

            if(end_time_date <= start_time_date) {
                view.showToast("Your dates are not in sequence. Please, try again.")
                return
            }

            val observableWithResponseToGo = promotionAPI.createPromotion(promotionModel.title, promotionModel.start_time, promotionModel.end_time, promotionModel.location)
            observableWithResponseToGo.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { promotionId ->
                                view.dismissAddPromotionDialogFragmentFromController()

                                if(promotionId != "") {
                                    promotionModel.promotion_id = promotionId as String
                                    promotionListModel.promotions.put(promotionModel, true)
                                    view.showToast("Thanks for adding an event!")
                                    view.setItemsList(promotionListModel.promotions) // TODO: better way to do it, reload data??
                                }else {
                                    view.showToast("Something went wrong. Please, check your internet connection, inputs and try again.")
                                }
                            },
                            { error ->
                                view.showToast("Something went wrong. Please, check your internet connection and try again.")
                                error.printStackTrace()
                            }
                    )
        }else {
            if(confirm) view.showToast("Something went wrong. Please, check your inputs and try again.")
            view.dismissAddPromotionDialogFragmentFromController()
        }
    }

}