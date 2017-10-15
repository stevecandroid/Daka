package com.xt.daka.util

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by steve on 17-10-7.
 */
class Regex{
    companion object {

        private fun match(regEx : String , content : String) : Matcher {
            val pattern = Pattern.compile(regEx)
            return pattern.matcher(content)
        }

        fun findall(regEx : String , content : String) : List<String>{
            val matcher = match(regEx,content)
            val result = mutableListOf<String>()
            while(matcher.find()){
                result.add(matcher.group())
            }
            return result
        }

        fun replaceAll(regEx : String , content : String, replace:String) : String{
            val matcher = match(regEx,content)
            return matcher.replaceAll(replace)
        }

    }
}