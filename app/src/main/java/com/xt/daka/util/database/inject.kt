package com.xt.daka.util.database

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

/**
 * Created by steve on 17-9-8.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Column( val isKey : Boolean = false,val pos : Int)



fun Collection<DataSupport>.dbRemoveAll() {
    async(CommonPool) {
        forEach { element ->
            element.delete()
        }
    }
}

fun Collection<DataSupport>.dbSaveAll() {
    async(CommonPool) {
        forEach { element ->
            element.save()
        }
    }
}
