package com.dev.megaloma.mvvmapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.megaloma.mvvmapplication.R
import com.dev.megaloma.mvvmapplication.ui.area_check.AreaCheckActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val prefer = getSharedPreferences("first_check", MODE_PRIVATE)
        //初回ログインの場合は、地域選択のActivityに移動
        if(prefer.getBoolean("first_check",true)){
            val intent = Intent(this, AreaCheckActivity::class.java)
            startActivity(intent)
        }else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
            }
        }
    }

}
