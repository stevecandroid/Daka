package com.xt.daka.util.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.SparseArray
import collections.forEach

/**
 * Created by steve on 17-9-21.
 */
class SQLOpenHelper(ctx: Context, tableName: String, fields: SparseArray<Pair<String, String>>, version: Int = 1)
    : SQLiteOpenHelper(ctx, tableName, null, version) {

    private val CREATE_BOOK: StringBuffer = StringBuffer()


    init {


        CREATE_BOOK.append("create table if not exists ${tableName} ("
                + "id integer primary key, ")

        fields.forEach{ _ ,(first, second) ->
            CREATE_BOOK.append("${first} ${paraseColumn(second)}, ")
        }

        CREATE_BOOK.replace(CREATE_BOOK.length - 2, CREATE_BOOK.length, ")")

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_BOOK.toString())
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun paraseColumn(initial: String): String {

        when (initial) {
            "String" -> return "text"
            "int", "short" -> return "integer"
            "boolean", "byte[]" -> return "blob"
            "double", "float" -> return "real"
        }

        return ""
    }

}