package com.xt.daka.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

import android.view.View
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by 铖哥 on 2017/8/11.
 */
class ClockView constructor(context: Context?,attributeSet: AttributeSet) : View(context,attributeSet) {


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(mWidth / 2f, mHeight / 2f)
        if(clockmode) {
            drawStaticSene(canvas)
            drawClock(canvas)
        }else{
            drawCircle(canvas)
        }
    }

    private fun drawCircle(canvas : Canvas){
//        mPiant.style = Paint.Style.FILL

        mPiant.color = Color.WHITE
        mPiant.style = Paint.Style.FILL
        val srcRectF = RectF(-width/2f,-height/2f,width.toFloat(),height.toFloat())
        val save = canvas.saveLayer(srcRectF, mPiant)
        canvas.drawRect(srcRectF, mPiant)
        mPiant.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawCircle(0f,0f,if(width < height) width/2f else height/2f, mPiant)
        mPiant.xfermode = null
        canvas.restoreToCount(save)

//        val save = canvas.saveLayer(0f,0f,width.toFloat(),height.toFloat(),null,Canvas.ALL_SAVE_FLAG)
//        mPiant.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
//        canvas.restoreToCount(save)

    }


    var clockmode = true
    set(value) {
        field = value
        postInvalidate()
    }

    var secondHandColor : Int = Color.rgb(193, 193, 193)
    var minuteHandColor : Int = Color.rgb(154, 154, 154)
    var hourHandColor : Int = Color.argb(218,113, 113, 113)

    var timeChange: ((String)-> Unit)? = null

    fun onTimeChanged( timeChange : (String)-> Unit){
        this.timeChange = timeChange
    }

    var curMinute = 0 ;

    val formatter = SimpleDateFormat("HH:mm:ss.SSS")
    val outFormatter = SimpleDateFormat("HH点mm分")


    private fun drawClock(canvas: Canvas){


        val time = formatter.format(Date())
        val trible = time.split(":")

        val s = trible[2].toDouble()
        val m = trible[1].toInt()+s/60
        val h = trible[0].toInt()+m/60

        if(timeChange != null && m.toInt() != curMinute){
            timeChange?.invoke(outFormatter.format(Date()))
        }



        var const = s * Math.PI / 30
        mPiant.color = secondHandColor
        canvas.drawLine(0f,0f, (clockRadius-gap) * Math.sin((const)).toFloat(),- (clockRadius-gap)*Math.cos((const)).toFloat(), mPiant)
        const = m * Math.PI/30
        mPiant.color = minuteHandColor
        canvas.drawLine(0f,0f,  (clockRadius-gap*3) * Math.sin((const)).toFloat() , -(clockRadius-gap*3) *Math.cos((const)).toFloat(), mPiant)
        const = h * Math.PI/6
        mPiant.color = hourHandColor
        canvas.drawLine(0f,0f,  (clockRadius-gap*5) * Math.sin(const).toFloat() , (clockRadius-gap*5) * Math.sin(const - Math.PI/2).toFloat(), mPiant)
        postInvalidateDelayed(30)

    }

    private  fun drawStaticSene(canvas : Canvas ){


        mPiant.color = DEFAULT_COLOR
        for(p in pList) {
            canvas.drawLine(p.first.x, p.first.y, p.second.x, p.second.y, mPiant)
        }
        canvas.drawCircle(0f,0f,10f, mPiant)

    }

    private val pList : MutableList<Pair<PointF,PointF>> = mutableListOf()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    var clockRadius : Int = DEFAULT_WIDTH
    var gap : Int = 0

    companion object {
        val DEFAULT_WIDTH = 500
        val DEFAULT_HEIGHT = 500
        val DEFAULT_COLOR = Color.GRAY
        val mPiant = Paint()
    }

    init {
        mPiant.isAntiAlias = true
        mPiant.style = Paint.Style.STROKE
        mPiant.color = DEFAULT_COLOR
        mPiant.strokeWidth  = 10f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.AT_MOST){
            mWidth = DEFAULT_WIDTH
        }

        if(heightMode == MeasureSpec.AT_MOST){
            mHeight = DEFAULT_HEIGHT
        }

        clockRadius = if(mWidth<mHeight) mWidth*2/5 else mHeight*2/5
        gap = clockRadius/7

        setMeasuredDimension(mWidth,mHeight)

        if(pList.size == 0){
            for( i in 0..360 step 30){
                val const = i*Math.PI*2/360
                val p1 = PointF(clockRadius * Math.sin((const)).toFloat(),-clockRadius*Math.cos((const)).toFloat())
                val p2 = PointF((clockRadius-gap) * Math.sin((const)).toFloat(),(clockRadius-gap)*Math.cos(const + Math.PI).toFloat())
                pList.add(Pair(p1,p2))
            }
        }

    }

}