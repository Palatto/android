package plate.plateandroid.App.PromotionList

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.promotion_list.*
import plate.plateandroid.App.PromotionList.Complements.*
import plate.plateandroid.R


/**
 * Created by rennerll on 11/24/17.
 */
class PromotionListActivity : AppCompatActivity(), PromotionListInterface.View, PromotionAdapter.OnItemClickListener, PromotionListDialogInterface {
    companion object {
        val USER_NAME = "userName"
        val NO_FOOD_DIALOG_TAG = "noFoodDialog"
        val ADD_PROMOTION_DIALOG_TAG = "addPromotionDialog"
    }

    val controller: PromotionListInterface.Controller = PromotionListController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.promotion_list)

        loading_icon.isIndeterminate = true
        loading_icon.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.mainRed), android.graphics.PorterDuff.Mode.MULTIPLY)

        val b: Bundle = intent.extras
        val username = b.getString(USER_NAME)

        controller.init(this, username)
        controller.initializePromotionList()

        promotionRefresh.setColorSchemeResources(R.color.mainRed, R.color.mainRed, R.color.mainRed)

        promotionRefresh.setOnRefreshListener({
            controller.refreshPromotionList()
        })

        addButtonPromotion.setOnClickListener {
            showAddPromotionDialogFragment()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PromotionListActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun setItemsList(promotions: MutableMap<PromotionModel, Boolean>) {
        with(promotionListItems) {
            addItemDecoration(PromotionListDivider(applicationContext))
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = PromotionAdapter(promotions, this@PromotionListActivity, this@PromotionListActivity)
        }
    }

    /**ADAPTER METHODS*/

    override fun OnItemClick(promotionModel: PromotionModel, firstClick: Boolean) {
        controller.respondToClick(promotionModel, firstClick)
    }

    /**DIALOG FRAGMENT METHODS FROM CONTROLLER*/

    override fun showNoFoodDialogFragmentFromController(promotionModel: PromotionModel) {
        showNoFoodDialogFragment(promotionModel)
    }

    override fun dismissNoFoodDialogFragmentFromController() {
        this.supportFragmentManager.findFragmentByTag(NO_FOOD_DIALOG_TAG)?.apply {
            (this as NoFoodDialogFragment).dismiss()
            this@PromotionListActivity.supportFragmentManager.beginTransaction().remove(this)
        }
    }

    override fun dismissAddPromotionDialogFragmentFromController() {
        this.supportFragmentManager.findFragmentByTag(ADD_PROMOTION_DIALOG_TAG)?.apply {
            (this as AddPromotionDialogFragment).dismiss()
            this@PromotionListActivity.supportFragmentManager.beginTransaction().remove(this)
        }
    }

    /**DIALOG FRAGMENT INTERFACE METHODS*/

    override fun showNoFoodDialogFragment(promotionModel: PromotionModel) {
        var noFoodDialog = NoFoodDialogFragment.newInstance(promotionModel)
        noFoodDialog.promotionListDialogInterface = this
        noFoodDialog.show(this@PromotionListActivity.supportFragmentManager.beginTransaction(), NO_FOOD_DIALOG_TAG)
    }

    override fun dismissNoFoodDialogFragment(confirm: Boolean, promotionModel: PromotionModel?) {
        controller.handleNoFoodDialog(confirm, promotionModel)
    }

    override fun showAddPromotionDialogFragment() {
        var addPromotionDialogFragment = AddPromotionDialogFragment.newInstance()
        addPromotionDialogFragment.promotionListDialogInterface = this
        addPromotionDialogFragment.show(this@PromotionListActivity.supportFragmentManager.beginTransaction(), ADD_PROMOTION_DIALOG_TAG)
    }

    override fun dismissAddPromotionDialogFragment(confirm: Boolean, promotionModel: PromotionModel?) {
        controller.handleAddPromotionDialog(confirm, promotionModel)
    }

    /**LOADING METHODS*/

    override fun hideLoading() {
        loading_layer.visibility = View.GONE
    }

    override fun showLoading() {
        loading_layer.visibility = View.VISIBLE
    }

    /**REFRESHING METHODS*/

    override fun stopRefreshing() {
        promotionRefresh.isRefreshing = false
    }

}