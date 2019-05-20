package com.dev.megaloma.kahuninfoapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.dev.megaloma.kahuninfoapp.R
import com.dev.megaloma.kahuninfoapp.ui.area_check.AreaCheckActivity
import com.dev.megaloma.kahuninfoapp.ui.kahun_resource.ResourceActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    private var mTabsPagerAdapter: TabsPagerAdapter? = null

    //オプションメニューの追加
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.action_settings_memo_detail){
            val intent = Intent(this, ResourceActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.action_settings_memo_detail2){
            val intent = Intent(this, OssLicensesMenuActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onResume() {
        super.onResume()
        val prefer = getSharedPreferences("DataSave", MODE_PRIVATE)
        //Log.d("MainActivityTest",prefer.getInt("city_code",0).toString())

        //初回ログインの場合は、地域選択のActivityに移動
        if(prefer.getInt("city_code",0)==0){
            Toast.makeText(this,"住んでいる近くの地域を選択してください",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AreaCheckActivity::class.java)
            startActivity(intent)
        }
        else {
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabsPagerAdapter = null
    }
}
