package plate.plateandroid.App.PromotionList.Complements

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import plate.plateandroid.App.PromotionList.PromotionModel
import plate.plateandroid.R

/**
 * Created by rennerll on 12/13/17.
 */
class NoFoodDialogFragment: DialogFragment() {
    var promotionListDialogInterface: PromotionListDialogInterface? = null // see open var
    lateinit var promotionModel: PromotionModel

    var titleText: AppCompatTextView? = null
    var confirmTextAll: AppCompatTextView? = null
    var cancelButton: Button? = null
    var concludeButton: Button? = null

    companion object {
        val PROMOTION_MODEL = "promotionModel"

        fun newInstance(promotionModel: PromotionModel): NoFoodDialogFragment {
            val fragment = NoFoodDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(PROMOTION_MODEL, promotionModel)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog)
        promotionModel = arguments?.getSerializable(PROMOTION_MODEL) as PromotionModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var view: View? = inflater.inflate(R.layout.fragment_dialog_no_food, container)
        bindView(view)
        loadValues()
        return view
    }

    fun bindView(view: View?) {
        titleText = view?.findViewById<AppCompatTextView>(R.id.titleText)
        confirmTextAll = view?.findViewById<AppCompatTextView>(R.id.confirmTextAll)

        cancelButton = view?.findViewById<Button>(R.id.cancelItemQuantityButton)
        cancelButton?.setOnClickListener {
            promotionListDialogInterface?.dismissNoFoodDialogFragment(false, null)
        }

        concludeButton = view?.findViewById<Button>(R.id.concludeItemQuantityButton)
        concludeButton?.setOnClickListener {
            promotionListDialogInterface?.dismissNoFoodDialogFragment(true, promotionModel)
        }
    }

    fun loadValues() {
        titleText?.text = "Are you sure?"

        val resultString = SpannableStringBuilder()

        resultString.append(regularSpannable("You are about to inform that "))
        resultString.append(greenBoldSpannable(promotionModel.title))
        resultString.append(regularSpannable(" at "))
        resultString.append(greenBoldSpannable(promotionModel.location))
        resultString.append(regularSpannable(" does not have any food left."))
        resultString.append(regularSpannable(" Is that accurate?"))

        confirmTextAll?.text = resultString
    }

    private fun regularSpannable(text: String): SpannableString {
        var partOfResult = SpannableString(text)
        partOfResult.setSpan(AbsoluteSizeSpan(16, true), 0, partOfResult.length, 0)
        partOfResult.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.mainGray)), 0, partOfResult.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return partOfResult
    }

    private fun greenBoldSpannable(text: String): SpannableString {
        var partOfResult = SpannableString(text)

        partOfResult.setSpan(AbsoluteSizeSpan(16, true), 0, partOfResult.length, 0)
        partOfResult.setSpan(StyleSpan(STYLE_NORMAL), 0, partOfResult.length, 0)
        partOfResult.setSpan(ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.mainRed)), 0, partOfResult.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // see unwrap

        return partOfResult
    }
}