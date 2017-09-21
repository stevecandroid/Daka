package com.xt.daka.util.database

import android.content.Context
import android.database.Cursor
import collections.forEach
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

/**
 * Created by steve on 17-9-21.
 */
class DbHelper <T : DataSupport> constructor(ctx: Context, private val claz: Class<out Any>) : BasicData(ctx,claz) {

//
//      fun query()   {
////          return db.query(tableName,null,null,null,null,null,null)
////          fields.forEach { i, (name ,type) ->  Log.e("DbHelper","${i  } ${name}   ") }
//      }

    fun query() : List<T>?{
        val result = mutableListOf<T>()
        val cursor = db.query(tableName,null,null,null,null,null,null)
        if(cursor != null && !cursor.moveToFirst()) return null;
        do{
            result.add(claz.constructors[0].newInstance(*getInitargs(cursor)) as T)
        }while (cursor.moveToNext())

        return result
    }

    fun getInitargs(cursor : Cursor) : Array<Any?>{
        val columns = mutableListOf<Any?>()

        fields.forEach{
            pos ,(column,type)->
            columns.add(getData(cursor,type,column))
        }

        return columns.toTypedArray()

    }

    fun getData(cursor : Cursor, type : String, column : String) : Any?{
        when (type) {
            "String" -> return cursor.getString(cursor.getColumnIndex(column))
            "short" -> return cursor.getShort(cursor.getColumnIndex(column))
            "int" -> return cursor.getInt(cursor.getColumnIndex(column))
            "byte[]" -> return cursor.getBlob(cursor.getColumnIndex(column))
            "boolean" -> return cursor.getBlob(cursor.getColumnIndex(column))[0].toInt() == 1
            "double" -> return cursor.getDouble(cursor.getColumnIndex(column))
            "float" -> return cursor.getFloat(cursor.getColumnIndex(column))
            else -> return null
        }
    }

    fun removeAll( e : List<T> , finish : () -> Unit){
        async(CommonPool){
            e.forEach {
                elem -> elem.delete()
            }
            finish.invoke()
        }
    }

    fun saveAll( e : List<T> , finish : () -> Unit){
        async(CommonPool){
            e.forEach {
                elem -> elem.save()
            }

            finish.invoke()
        }
    }

}