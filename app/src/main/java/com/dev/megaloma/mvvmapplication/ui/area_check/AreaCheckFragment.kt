package com.dev.megaloma.mvvmapplication.ui.area_check

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.megaloma.mvvmapplication.R

class AreaCheckFragment : Fragment() {

    var mView: View? = null
    var mRecyclerView: RecyclerView? = null

    companion object {
        fun newInstance() = AreaCheckFragment()
    }

    private lateinit var viewModel: AreaCheckViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mView = inflater.inflate(R.layout.area_check_fragment, container, false)
        // RecyclerViewの参照を取得
        mRecyclerView = mView!!.findViewById(R.id.recycler_view)
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        //境界線の描画
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mRecyclerView!!.addItemDecoration(dividerItemDecoration)

        // Adapterの設定
        val recyclerViewAdapter =  RecyclerViewAdapter(getListData())
        mRecyclerView!!.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnItemClickListener(object : RecyclerViewAdapter.onItemClickListener {
            override fun onClick(view: View, name: String) {
                Log.d("Test","$name clicked")

            }
        })
        return mView!!
    }

   private fun getListData():List<String>{
       val list: ArrayList<String> = ArrayList()
       for (s in resources.getStringArray(R.array.prefecture_names)) {
           list.add(s)
       }
       return list
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AreaCheckViewModel::class.java)
    }

}
