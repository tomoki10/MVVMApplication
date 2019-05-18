package com.dev.megaloma.kahuninfoapp.ui.area_check

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.megaloma.kahuninfoapp.R

class AreaCheckActivity : AppCompatActivity(), AreaCheckFragment.OnFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.area_check_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AreaCheckFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onFragmentFinish(){
        finish()
    }
}
