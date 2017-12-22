package plate.plateandroid.App.PromotionList.Complements

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import plate.plateandroid.R


/**
 * Created by rennerll on 12/20/17.
 */

class PromotionListDivider(context: Context) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable

    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.promotion_list_divider)!!
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}