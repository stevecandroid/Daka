package com.xt.daka.base

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.xt.daka.util.autoNotify
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by steve on 17-9-21.
 */
abstract class  BaseAdapter<T, V :  BaseItemView<T>>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mDatas : List<T> by Delegates.observable( mutableListOf() ){
        property, oldValue, newValue ->

        autoNotify(oldValue,newValue){ t1 , t2 ->  false }

    }

    constructor( mDatas : List<T>) : this(){
        this.mDatas = mDatas
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder!!.itemView as V).bind(mDatas.get(holder.adapterPosition),holder.adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val holder =  object : RecyclerView.ViewHolder(createView(parent!!.context) as View){}
        holder.itemView.setOnClickListener(click)
        holder.itemView.setOnLongClickListener(longClick)
        return holder
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    abstract fun createView(ctx : Context) : V

    var click : ((View)->Unit)? = null
    var longClick : ((View)->Boolean)? = null

    fun setOnItemClickListener( click : (View)->Unit){
        this.click = click
    }

    fun setOnItemLongClickListener( click : (View)->Boolean){
        this.longClick = click
    }


}



