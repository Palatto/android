package plate.plateandroid.App.PromotionList.Complements

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.promotion_card.view.*
import plate.plateandroid.App.PromotionList.PromotionModel
import plate.plateandroid.App.Utils.ViewHolder
import plate.plateandroid.R
import plate.plateandroid.R.layout.promotion_card

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionAdapter constructor(var promotions: MutableMap<PromotionModel, Boolean>, var context: Context, var onClickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // solve context as soon as possible
    var promotionsArray =  promotions.keys.toMutableList().sortedWith( compareBy<PromotionModel>({ it.start_time }) )
    var contextParameter = context

    override fun getItemCount(): Int {
        return promotions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(promotion_card, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val promotionModel = promotionsArray[position]
        holder?.itemView?.title?.text = promotionModel.title
        holder?.itemView?.location?.text = promotionModel.location
        holder?.itemView?.time?.text = promotionModel.getTime()
        val button = holder?.itemView?.goingButton as AppCompatButton

        if(promotions[promotionModel] == false) {
            button.setOnClickListener {
                onClickListener.OnItemClick(promotionModel, false)
            }
            button.setText("IS THE FOOD OVER?")
            button.setTextColor(ContextCompat.getColor(contextParameter, android.R.color.darker_gray))
        } else {
            button.setOnClickListener {
                onClickListener.OnItemClick(promotionModel, true)
            }
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(promotionModel: PromotionModel, firstClick: Boolean)
    }
}
