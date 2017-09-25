package com.xt.daka.network.youtu

import com.xt.daka.util.encrypt.Base64Util
import com.xt.daka.util.encrypt.HMACSHA1
import java.util.*

/**
* Created by steve on 17-9-16.
*/


/**
 * app_sign    时效性签名
 * @param  appId       http://open.youtu.qq.com/上申请的业务ID
 * @param  secret_id   http://open.youtu.qq.com/上申请的密钥id
 * @param  secret_key  http://open.youtu.qq.com/上申请的密钥key
 * @param  expired     签名过期时间
 * @param  userid      业务账号系统,没有可以不填
 * @param  mySign      生成的签名
 * @return 0表示成功
 */
fun appSign(appId: String, secret_id: String, secret_key: String,
            expired: Long, userid: String, mySign: StringBuffer): Int {
    return appSignBase(appId, secret_id, secret_key, expired, userid, null, mySign)
}


private fun appSignBase(appId: String, secret_id: String,
                        secret_key: String, expired: Long, userid: String, url: String?,
                        mySign: StringBuffer): Int {


    if (empty(secret_id) || empty(secret_key)) {
        return -1
    }

    var puserid = ""
    if (!empty(userid)) {
        if (userid.length > 64) {
            return -2
        }
        puserid = userid
    }


    val now = System.currentTimeMillis() / 1000
    val rdm = Math.abs(Random().nextInt())
    val plain_text = "a=$appId&k=$secret_id&e=$expired&t=$now&r=$rdm&u=$puserid"//+ "&f=" + fileid.toString();

    val bin = hashHmac(plain_text, secret_key)

    val all = ByteArray(bin!!.size + plain_text.toByteArray().size)
    System.arraycopy(bin, 0, all, 0, bin.size)
    System.arraycopy(plain_text.toByteArray(), 0, all, bin.size, plain_text.toByteArray().size)

    mySign.append(Base64Util.encode(all))

    return 0
}

private fun hashHmac(plain_text: String, accessKey: String): ByteArray? {

    try {
        return HMACSHA1.getSignature(plain_text, accessKey)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

}


fun empty(s: String?): Boolean {
    return s == null || s.trim { it <= ' ' } == "" || s.trim { it <= ' ' } == "null"
}

