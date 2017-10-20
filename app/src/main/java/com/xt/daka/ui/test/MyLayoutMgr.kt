package com.xt.daka.ui.test

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.View

/**
 * Created by steve on 17-9-25.
 */
class MyLayoutMgr : RecyclerView.LayoutManager() {

    private var mVerticalOffset: Int = 0//竖直偏移量 每次换行时，要根据这个offset判断
    private var mFirstVisiPos: Int = 0//屏幕可见的第一个View的Position
    private var mLastVisiPos: Int = 0//屏幕可见的最后一个View的Position
    private val mItemRect: SparseArray<Rect> = SparseArray()


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun measureChildWithMargins(child: View?, widthUsed: Int, heightUsed: Int) {
        super.measureChildWithMargins(child, widthUsed, heightUsed)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (itemCount == 0) run { detachAndScrapAttachedViews(recycler); return }

        detachAndScrapAttachedViews(recycler)

        mLastVisiPos = itemCount

        fill(recycler, state, 0)


    }

    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?, dy: Int) {

        var topOffset = paddingTop;


        if (childCount > 0) {
            for (i in 0 until childCount) {
                val view = recycler!!.getViewForPosition(i)
                if (dy > 0) {
                    if (getDecoratedBottom(view) - dy > topOffset) {
                        removeAndRecycleView(view, recycler)
                        mFirstVisiPos++
                        mLastVisiPos++
                        continue
                    }
                } else if (dy < 0) {
                    getDecoratedTop(view) - dy > height - paddingBottom
                    removeAndRecycleView(view, recycler);
                    mLastVisiPos--
                    mFirstVisiPos--
                }
            }
        }


        if (dy > 0) {
            mLastVisiPos = itemCount - 1;
            var itemHeight = 0
            if (childCount > 0) {
                val lastView = getChildAt(childCount - 1)
                topOffset = getDecoratedTop(lastView)
            }

            for (i in mFirstVisiPos..mLastVisiPos) {
                val child = recycler!!.getViewForPosition(i)
                addView(child)
                measureChildWithMargins(child, 0, 0)
                itemHeight = getDecoratedMeasurementVertical(child)

                if (topOffset - dy > height - paddingBottom) {
                    removeAndRecycleView(child, recycler)
                } else {
                    layoutDecoratedWithMargins(child, 0,
                            mVerticalOffset + topOffset,
                            getDecoratedMeasurementHorizontal(child),
                            itemHeight + topOffset)
                }

            }
        }


    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val offY = dy
        offsetChildrenVertical(-offY)
        fill(recycler, state, 0)
        return offY
    }

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    fun getDecoratedMeasurementHorizontal(view: View): Int {
        val params = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    fun getDecoratedMeasurementVertical(view: View): Int {
        val params = view.layoutParams as RecyclerView.LayoutParams
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin
    }
}