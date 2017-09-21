package com.xt.daka.util.database

import android.content.ContentValues
import android.content.Context
import collections.forEach

/**
 * Created by steve on 17-9-21.
 */
open class DataSupport(ctx: Context) : BasicData(ctx) {

    private fun insert() {
        val values = getValues()
        db.insert(tableName, null, values)
    }

    fun delete() {
        db.delete(tableName, "${keyValue!!.first} = ? ", arrayOf(keyValue!!.second(this).toString()))
    }

    fun save() {
        val values = getValues()

        if (canfindThis()) {

            db.update(tableName, values, "${keyValue!!.first} = ? ", arrayOf(keyValue!!.second(this).toString()))
        } else {

            insert()
        }
    }

    fun canfindThis(): Boolean {

        val cursor = db.query(tableName, arrayOf(keyValue!!.first), null, null, null, null, null)
        if (!cursor.moveToFirst()) return false

        val keyColumn = keyValue!!.first
        val key = keyValue!!.second.invoke(this) as Int
        do {
            if (cursor.getInt(cursor.getColumnIndex(keyColumn)) == key) {
                cursor.close()
                return true
            }
        } while (cursor.moveToNext())
        cursor.close()
        return false
    }


    private fun getValues(): ContentValues {

        val values = ContentValues()

        methods.forEach { i, m ->
            val content = m.invoke(this)
            buildValues(values, fields[i].first, content)
        }

        return values

    }

    private fun buildValues(values: ContentValues, key: String, content: Any): Unit {

        when (content) {
            is String -> values.put(key, content)
            is Int -> values.put(key, content)
            is Long -> values.put(key, content)
            is Float -> values.put(key, content)
            is Double -> values.put(key, content)
            is Boolean -> values.put(key, content)
            is Short -> values.put(key, content)
            is ByteArray -> values.put(key, content)
        }
    }


}