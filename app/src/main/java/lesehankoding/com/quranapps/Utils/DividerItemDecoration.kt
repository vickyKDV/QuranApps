package lesehankoding.com.quranapps.Utils

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DividerItemDecoration(context: Context, orientation: Int, isShowInLastItem: Boolean) : ItemDecoration() {
    private val mIsShowInLastItem: Boolean
    private var mDivider: Drawable?
    //    private final Rect mBounds = new Rect();
    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation = 0

    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        mOrientation = orientation
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        requireNotNull(drawable) { "Drawable cannot be null." }
        mDivider = drawable
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || mDivider == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right,
                    parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        val childCount: Int
        childCount = if (mIsShowInLastItem) {
            parent.childCount
        } else {
            parent.childCount - 1
        }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            //                parent.getDecoratedBoundsWithMargins(child, mBounds);
//                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            val decoratedBottom = parent.layoutManager!!.getDecoratedBottom(child)
            val bottom = decoratedBottom + Math.round(child.translationY)
            val top = bottom - mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top,
                    parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }
        val childCount: Int
        childCount = if (mIsShowInLastItem) {
            parent.childCount
        } else {
            parent.childCount - 1
        }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            //                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
//                final int right = mBounds.right + Math.round(child.getTranslationX());
            val decoratedRight = parent.layoutManager!!.getDecoratedRight(child)
            val right = decoratedRight + Math.round(child.translationX)
            val left = right - mDivider!!.intrinsicWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mDivider == null) {
            outRect.setEmpty()
            return
        }
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val itemCount = state.itemCount
        if (mIsShowInLastItem) {
            if (mOrientation == VERTICAL) {
                outRect[0, 0, 0] = mDivider!!.intrinsicHeight
            } else {
                outRect[0, 0, mDivider!!.intrinsicWidth] = 0
            }
        } else if (itemPosition == itemCount - 1) {
            // We didn't set the last item when mIsShowInLastItem's value is false.
            outRect.setEmpty()
        } else {
            if (mOrientation == VERTICAL) {
                outRect[0, 0, 0] = mDivider!!.intrinsicHeight
            } else {
                outRect[0, 0, mDivider!!.intrinsicWidth] = 0
            }
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
        private const val TAG = "DividerItem"
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }

    /**
     * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
     *
     * @param context          Current context, it will be used to access resources.
     * @param orientation      Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
     * @param isShowInLastItem Whether show the divider in last item.
     */
    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        if (mDivider == null) {
            Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this "
                    + "DividerItemDecoration. Please set that attribute all call setDrawable()")
        }
        a.recycle()
        setOrientation(orientation)
        mIsShowInLastItem = isShowInLastItem
    }
}