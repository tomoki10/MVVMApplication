package com.dev.megaloma.mvvmapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.dev.megaloma.mvvmapplication.R
import com.dev.megaloma.mvvmapplication.ui.area_check.AreaCheckActivity
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    private var mTabsPagerAdapter: TabsPagerAdapter? = null

    // onCreateでFragmentが生成されているかのフラグ
    private var fragmentCreateflag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val tabsFragments = arrayListOf(
                MainFragment::class.java,
                StatFragment::class.java
        )

        //ViewPagerに既存のFragmentを関連づける

        //Adapterの生成
        val setTabName = arrayListOf(getString(R.string.main_tab_name1),
                getString(R.string.main_tab_name2))
        mTabsPagerAdapter = TabsPagerAdapter(supportFragmentManager, tabsFragments, setTabName)

        // ViewPagerにAdapterを設定
        viewPager.adapter = mTabsPagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> Log.d("ViewPager1","1")
                    1 -> Log.d("ViewPager1","2")
                }
            }
        })
        //　タブレイアウトへの関連付け
        tabLayout.setupWithViewPager(viewPager)

        val prefer = getSharedPreferences("first_check", MODE_PRIVATE)
        //初回ログインの場合は、地域選択のActivityに移動
        if(prefer.getBoolean("first_check",true)){
            val intent = Intent(this, AreaCheckActivity::class.java)
            startActivity(intent)
        }else {
            if (savedInstanceState == null && !fragmentCreateflag) {
                fragmentCreateflag = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabsPagerAdapter = null
    }
}
