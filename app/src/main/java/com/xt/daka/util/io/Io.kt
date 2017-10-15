package com.xt.daka.util.io

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.doAsync
import java.io.*

/**
 * Created by steve on 17-9-22.
 */
fun ByteArray.writeTo(path:String ){
    createFile(path)
    val os = FileOutputStream(path)
    async(CommonPool) {    os.write(this@writeTo) ; os.close() }
}

fun ByteArray.writeTo(file: File ){
    createFile(file)
    val os = FileOutputStream(file)
    async(CommonPool) {    os.write(this@writeTo) ; os.close()  }
}

fun String.writeTo(path:String ){
    createFile(path)
    val os = FileOutputStream(path)
    val sos = PrintStream(os,true)
    async(CommonPool) {    sos.print(this@writeTo) ; os.close() }
}

fun createFile(path: String) : Boolean{
    val file = File(path)
    if(!file.exists()){
        return file.createNewFile()
    }else{
        return false;
    }
}

fun createFile(file: File) : Boolean{

    try{
        if(!file.exists()){
            return file.createNewFile()
        }else{
            return false;
        }
    }catch ( e : IOException){
        e.printStackTrace()
    }

    return false
}

