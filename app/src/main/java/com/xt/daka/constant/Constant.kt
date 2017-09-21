package com.data.xt.daka.constant

/**
 * Created by steve on 17-9-15.
 */
object Constant {
    val hash= "23861923"
    val userId = "214652773"
    val appId = "10098915"
    val secretID = "AKIDrgkisvgHeKtcaboM1wkbSlvvoW4fm7DP"
    val secretKey = "Ih1FIcYfgdSPPYjvy7AcpHnDi7OuOqOt"
    val original = "u=${userId}&a=${appId}&k=${secretKey}&e=${System.currentTimeMillis()/1000+60*60*24}" +
            "&t=${System.currentTimeMillis()/1000}&r=${hash}&f="
}