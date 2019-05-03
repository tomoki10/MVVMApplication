package com.dev.megaloma.mvvmapplication.ui.main

import java.text.SimpleDateFormat
import java.util.*

class AccessTimeUtils{
    companion object {
        /*
         * 年月日時取得用メソッド
         * 花粉サイトは1-24時間表記のためその部分は補正
         * @param    カレンダーインスタンス
         * @return   日付文字列（yyyyMMddHH)
         */
        fun getRequestDateTime(calendar: Calendar):String{
            val simpleDateFormatYM = SimpleDateFormat("yyyyMM", Locale.JAPAN)
            val simpleDateFormatD = SimpleDateFormat("dd", Locale.JAPAN)
            val simpleDateFormatHh = SimpleDateFormat("HH", Locale.JAPAN)
            var returnDateTimeStr = ""

            //花粉症サイトの更新頻度に合わせて時刻を調整
            calendar.add(Calendar.HOUR, -1)

            // 0時の場合は、1-24時間表記に補正する
            returnDateTimeStr += if("0" == calendar.get(Calendar.HOUR).toString()){
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                //calendar.time呼び出しでは減算が更新されないためgetで呼び出す
                simpleDateFormatYM.format(calendar.time) +
                        calendar.get(Calendar.DATE).toString() + "24"
            }else{
                simpleDateFormatYM.format(calendar.time) +
                        simpleDateFormatD.format(calendar.time) +
                        simpleDateFormatHh.format(calendar.time)
            }
            return returnDateTimeStr
        }
    }
}