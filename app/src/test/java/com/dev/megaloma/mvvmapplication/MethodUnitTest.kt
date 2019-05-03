package com.dev.megaloma.mvvmapplication

import com.dev.megaloma.mvvmapplication.ui.area_check.TransrateCityNameToCityCode
import com.dev.megaloma.mvvmapplication.ui.main.AccessTimeUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MethodUnitTest {

    @Test
    fun method_TranslateCityNameToCityCode(){
        val currentKey = "札幌市"
        val keys = arrayOf("札幌市","函館市","旭川市")
        val values = arrayOf(50110100,50110200,50120100)
        val res = TransrateCityNameToCityCode().convert(currentKey,keys,values)
        assertEquals(res,50110100)
    }

    @Test
    fun method_TimeUtils_getRequestDateTime(){
        val calendar = Calendar.getInstance(Locale.JAPAN)

        //通常呼び出しの確認
        val sdf = SimpleDateFormat("yyyyMMddHH",Locale.JAPAN)
        val tmpCalendar = Calendar.getInstance(Locale.JAPAN)
        tmpCalendar.add(Calendar.HOUR, -1)
        assertEquals(sdf.format(tmpCalendar.time),AccessTimeUtils.getRequestDateTime(calendar))

        //通常時(日跨ぎのないパターン)
        calendar.set(2019,3,30,11,0,0)
        val normalTest = AccessTimeUtils.getRequestDateTime(calendar)
        assertEquals("2019043010",normalTest)

        //日跨ぎパターン(時刻のみ)
        calendar.set(2019,3,30,1,0,0)
        val spanningTimeTest = AccessTimeUtils.getRequestDateTime(calendar)
        assertEquals("2019042924",spanningTimeTest)

        //日跨ぎパターン(日付+時刻のみ)
        calendar.set(2019,4,1,1,0,0)
        val spanningDayAndTimeTest = AccessTimeUtils.getRequestDateTime(calendar)
        assertEquals("2019043024",spanningDayAndTimeTest)

        //年跨ぎパターン(年度+日付+時刻のみ)
        calendar.set(2019,0,1,1,0,0)
        val spanningYearAndDayAndTimeTest = AccessTimeUtils.getRequestDateTime(calendar)
        assertEquals("2018123124",spanningYearAndDayAndTimeTest)


    }

}
