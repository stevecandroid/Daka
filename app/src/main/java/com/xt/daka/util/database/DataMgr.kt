package com.xt.daka.util.database


import android.database.sqlite.SQLiteDatabase
import android.util.SparseArray
import java.lang.reflect.Method

/**
 * Created by steve on 17-9-9.
 */


/**
 *
 */

object DataMgr{
    // key tableName
    // getmethod list
    // property list
    val database : MutableMap<String,
            Pair<SparseArray<Method>,
                    SparseArray<Pair<String,String>>>> = mutableMapOf()

    val keyValue : MutableMap<String,Pair<String, Method>> = mutableMapOf()

    val SQLitedataBase : MutableMap<String,SQLiteDatabase> = mutableMapOf()

    fun clear(){
        database.clear()
        keyValue.clear()
        SQLitedataBase.clear()
    }



}

