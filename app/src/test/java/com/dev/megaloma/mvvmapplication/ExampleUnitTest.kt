package com.dev.megaloma.mvvmapplication

import com.dev.megaloma.mvvmapplication.ui.area_check.TransrateCityNameToCityCode
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun Method_TransrateCityNameToCityCode(){
        val currentKey = "札幌市"
        val keys = arrayOf("札幌市","函館市","旭川市")
        val values = arrayOf(50110100,50110200,50120100)
        val res = TransrateCityNameToCityCode().convert(currentKey,keys,values)
        assertEquals(res,50110100)
    }
}
