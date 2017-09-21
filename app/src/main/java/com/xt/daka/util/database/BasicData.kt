package com.xt.daka.util.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.SparseArray
import collections.forEach
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.lang.reflect.Method


/**
 * Created by steve on 17-9-8.
 */


abstract class BasicData(ctx: Context,claz: Class< out Any>? = null){

    protected val clazzName : String = if(claz != null) claz.name else this::class.java.name // 用于反射类

    protected val tableName : String = if(claz != null) claz.simpleName else this::class.java.simpleName //用于表名

    protected var db: SQLiteDatabase    // 打开后的数组库,由mgr缓存

    protected val fields: SparseArray<Pair<String, String>> // field.Name to typeName 变量名 和 变量类型名 mgr缓存

    protected val methods: SparseArray<Method>              // getMethods 和 fields 一一对应  mgr缓存

    protected var keyValue: Pair<String, Method>? = null  // 关键列名,关键列名属性获取方法

    init {

        var noKey = true
        val clazz = Class.forName(clazzName)

        val method_field = DataMgr.database[tableName]
        if (method_field != null && null != DataMgr.keyValue[tableName]) {
            methods = method_field.first
            fields = method_field.second
            keyValue = DataMgr.keyValue[tableName]
        } else {

            fields = SparseArray()
            methods = SparseArray()

            clazz.declaredFields.map { field ->
                //得到所有列 并转化为 pos to Pair 变量名 to 变量类型 ,空字符串表示非列元素
                var annotation: Column? = null
                var pos : Int

                if (null != { annotation = field.getAnnotation(Column::class.java); annotation }()) {

                    if (annotation!!.isKey) {

                        keyValue = field.name to clazz.getMethod("get${field.name}")

                        DataMgr.keyValue.put(tableName, keyValue!!)

                        noKey = false

                    }

                    Triple(annotation!!.pos,field.name,field.type.simpleName)

//                    pos  to field.name to field.type.simpleName

                } else Triple(-1,"","")


            }.filter { (id) -> id != -1 } //过滤非列的元素.
                    .forEach { trible ->
                        run {
                            //得到列名于列变量 及其类型并存放
                            fields.put(trible.first,trible.second to trible.third)
                            //得到所有列的get方法并存放
                            methods.put(trible.first,clazz.getMethod("get${trible.second}"))
                        }
                    }

            if (noKey) throw Exception("no Key Exception ,you must have one keyColumn Annotation")

            DataMgr.database.put(tableName, methods to fields)
        }

        db = {
            if (DataMgr.SQLitedataBase[tableName] == null) {
                val tdb = SQLOpenHelper(ctx, tableName, fields).writableDatabase
                DataMgr.SQLitedataBase.put(tableName, tdb)
                tdb
            } else {
                DataMgr.SQLitedataBase[tableName]!!
            }
        }()

    } // 初始化所有BasicData 并且 缓存
}




