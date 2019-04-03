package com.dev.megaloma.mvvmapplication.ui.area_check

import android.arch.lifecycle.ViewModelProviders
import android.content.res.TypedArray
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
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
        mView = inflater.inflate(com.dev.megaloma.mvvmapplication.R.layout.area_check_fragment, container, false)
        // RecyclerViewの参照を取得
        mRecyclerView = mView!!.findViewById(com.dev.megaloma.mvvmapplication.R.id.recycler_view)
        // レイアウトマネージャを設定(ここで縦方向の標準リストであることを指定)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        //境界線の描画
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mRecyclerView!!.addItemDecoration(dividerItemDecoration)

        // Adapterの設定
        val recyclerViewAdapter =  RecyclerViewAdapter(convertArrayToList(R.array.prefecture_names))
        mRecyclerView!!.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnItemClickListener(object : RecyclerViewAdapter.onItemClickListener {
            override fun onClick(view: View, name: String) {
                Log.d("Test","$name clicked")

                Log.d("Test","packname $context.packageName")

                // クリックしたアイテムの位置を取得
                val itemId = resources.getIdentifier(name,"string",context!!.packageName)

                Log.d("Test","itemId $itemId")
                Log.d("Test","getStr ${resources.getString(itemId)}")

                // 都道府県コードに応じた市の配列を返す
                val items = prefectureList(Integer.parseInt(resources.getString(itemId)))

                // デフォルトでチェックされているアイテム
                val defaultItem = 0
                var checkedItem: Int = defaultItem
                AlertDialog.Builder(context!!)
                        .setTitle("Selector")
                        .setSingleChoiceItems(items, defaultItem) { _, which ->
                            checkedItem = which
                        }
                        .setPositiveButton("OK") { _,  _->
                            Log.d("checkedItem:", "" + items.get(checkedItem))
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
            }
        })
        return mView!!
    }

   private fun convertArrayToList(arrayRCode: Int):List<String>{
       val list: ArrayList<String> = ArrayList()
       for (s in resources.getStringArray(arrayRCode)) {
           list.add(s)
       }
       return list
    }

    private fun prefectureList(prefectureIndex: Int):Array<String>{
        val typedArray: TypedArray = resources.obtainTypedArray(R.array.city_parent)
        val result = resources.getStringArray(typedArray.getResourceId(prefectureIndex-1,0))
        typedArray.recycle()
        return result
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AreaCheckViewModel::class.java)
    }

}
