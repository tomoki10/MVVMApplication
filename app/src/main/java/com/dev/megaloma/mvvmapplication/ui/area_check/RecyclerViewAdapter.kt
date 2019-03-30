package com.dev.megaloma.mvvmapplication.ui.area_check

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.megaloma.mvvmapplication.R

class RecyclerViewAdapter// [4]
(private var dataSet: List<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // [3]
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textView: TextView = v.findViewById<View>(R.id.list_item_text) as TextView
    }

    // [5] viewを作成し返す
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_area_item, parent, false)
        return ViewHolder(v)
    }

    // [6] ViewHolderにデータをセット
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = dataSet[position]
        holder.textView.text = text
    }

    // [7]
    override fun getItemCount(): Int {
        return dataSet.size
    }
}