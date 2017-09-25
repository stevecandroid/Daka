//package com.xt.daka.network.youtu.data.model
//
//import com.google.gson.Gson
//import com.google.gson.TypeAdapter
//import com.google.gson.reflect.TypeToken
//import com.xt.daka.network.exception.ApiException
//import okhttp3.MediaType
//import okhttp3.RequestBody
//import okhttp3.ResponseBody
//import okio.Buffer
//import retrofit2.Converter
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.io.IOException
//import java.io.OutputStreamWriter
//import java.lang.reflect.Type
//import java.nio.charset.Charset
//import java.io.ByteArrayInputStream
//import java.io.InputStreamReader
//
//
///**
// * Created by steve on 17-9-22.
// */
//class ErrorConverterFactory private constructor(val gson: Gson) : Converter.Factory() {
//
//    companion object {
//
//        fun create(): ErrorConverterFactory {
//        return ErrorConverterFactory(Gson())
//    }
//
//    }
//
//
//
//    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
//        val adapter = gson.getAdapter(TypeToken.get(type))
//
//        return ErrorResponsebodyConverter(gson, adapter)
//    }
//
//    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>?,
//                                      methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
//        val adapter = gson.getAdapter(TypeToken.get(type!!))
//        return ErrorRequestBodyConverter(gson, adapter)
//    }
//}
//
//class ErrorRequestBodyConverter<T> constructor(private val gson: Gson, private val adapter: TypeAdapter<T>): Converter<T,RequestBody>{
//
//    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
//    private val UTF_8 = Charset.forName("UTF-8")
//
//
//    @Throws(IOException::class)
//    override fun convert(value: T): RequestBody {
//        val buffer = Buffer()
//        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
//        val jsonWriter = gson.newJsonWriter(writer)
//        adapter.write(jsonWriter, value)
//        jsonWriter.close()
//        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
//    }
//
//}
//
//class ErrorResponsebodyConverter <T> constructor(private val gson : Gson , private val adapter : TypeAdapter<T>) : Converter<ResponseBody,T>{
//
//
//
//    @Throws(IOException::class,ApiException::class)
//    override fun convert(value: ResponseBody): T {
//
//        val response : String = gson.toString()
//        val httpStatus = gson.fromJson(response,HttpStatus::class.java)
//        if(!httpStatus.isAccessable()){
//            throw ApiException(httpStatus)
//        }
//
//        val contentType = value.contentType()
//        val charset = if(contentType != null) contentType.charset(Charsets.UTF_8) else Charsets.UTF_8
//        val inputStream = ByteArrayInputStream(response.toByteArray());
//        val reader = InputStreamReader(inputStream, charset);
//        val jsonReader = gson.newJsonReader(reader);
//
//
//        try {
//            return adapter.read(jsonReader)
//        } finally {
//            value.close()
//        }
//    }
//
//
//}