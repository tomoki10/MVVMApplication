package com.dev.megaloma.mvvmapplication

import com.google.gson.annotations.SerializedName

data class KahunData(
        @SerializedName ("SOKUTEI_KYOKU_CODE") val SOKUTEI_KYOKU_CODE: String,
        @SerializedName ("AMEDASU_SOKUTEI_KYOKU_CODE") val AMEDASU_SOKUTEI_KYOKU_CODE: String,
        @SerializedName ("DATE_TIME") val DATE_TIME: String, //YYYYMMDDhh
        @SerializedName ("SOKUTEI_KYOKU_NAME") val SOKUTEI_KYOKU_NAME: String,
        @SerializedName ("SOKUTEI_KYOKU_SYUBETU") val SOKUTEI_KYOKU_SYUBETU: String,
        @SerializedName ("PREFECTURES_CODE") val PREFECTURES_CODE: String,
        @SerializedName ("PREFECTURES") val PREFECTURES: String,
        @SerializedName ("CITY_CODE") val CITY_CODE: String,
        @SerializedName ("CITY") val CITY: String,
        @SerializedName ("KAHUN_HISAN") val KAHUN_HISAN: String,
        @SerializedName ("WIND_DIRECTION") val WIND_DIRECTION: String,
        @SerializedName ("WIND") val WIND: String,
        @SerializedName ("TEMPERATURE") val TEMPERATURE: String,
        @SerializedName ("PRECIPITATION_AMOUNT") val PRECIPITATION_AMOUNT: String,
        @SerializedName ("RADAR_PRECIPITATION_AMOUNT") val RADAR_PRECIPITATION_AMOUNT: String)