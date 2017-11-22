package plate.plateandroid.Main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.promotion_card.view.*
import plate.plateandroid.R.layout.promotion_card

/**
 * Created by rennerll on 11/20/17.
 */
class PromotionAdapter constructor(var promotions: MutableList<PromotionModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)

        return ViewHolder(layoutInflater.inflate(promotion_card, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var promotion = promotions[position]

        holder?.itemView?.title?.text = promotion.title
        holder?.itemView?.location?.text = promotion.location
        holder?.itemView?.time?.text = promotion.getTime()
    }

    override fun getItemCount(): Int {
        return promotions.size
    }
}