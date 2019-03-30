package com.dev.megaloma.mvvmapplication.ui.area_check

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.megaloma.mvvmapplication.R

class AreaCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.area_check_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AreaCheckFragment.newInstance())
                    .commitNow()
        }
    }

}
