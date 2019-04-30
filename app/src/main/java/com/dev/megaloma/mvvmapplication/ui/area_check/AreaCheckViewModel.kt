package com.dev.megaloma.mvvmapplication.ui.area_check

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v7.widget.RecyclerView

class AreaCheckViewModel : ViewModel() {
    private var mRecyclerView: MutableLiveData<RecyclerView> = MutableLiveData()
    var recyclerView: MutableLiveData<RecyclerView> = mRecyclerView
    fun setRecyclerView(recyclerView :RecyclerView){
        mRecyclerView.postValue(recyclerView)
    }
}
