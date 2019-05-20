package com.dev.megaloma.kahuninfoapp.ui.area_check

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.dev.megaloma.kahuninfoapp.R

class RecyclerViewAdapter(private var dataSet: List<String>) :
        RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

    private var listener: onItemClickListener? = null

    // RecyclerViewAdapter$onItemClickListenerviewを作成し返す
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_area_item, parent, false)
        return CustomViewHolder(v)
    }

    // ViewHolderにデータをセット
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val text = dataSet[position]
        holder.textView.text = text
        holder.linearLayout.id = holder.adapterPosition
        holder.linearLayout.setOnClickListener { view ->
            listener!!.onClick(view, dataSet[holder.adapterPosition], holder.adapterPosition) }
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    interface onItemClickListener {
        fun onClick(view: View, name: String, position: Int)
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout) as LinearLayout
        val textView: TextView = itemView.findViewById(R.id.list_item_text) as TextView
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

