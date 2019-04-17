package com.dev.megaloma.mvvmapplication.ui.area_check

class TransrateCityNameToCityCode(){
    fun convert(targetCityName: String, cityNameArr: Array<String>, cityCode: Array<Int>): Int? {
        val map: Map<String, Int> = cityNameArr.zip(cityCode).toMap()
        return if(map[targetCityName]!=null) map[targetCityName] else 0
    }
}