package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.widget.ImageView

class MainViewModel : ViewModel() {

    private var mName: MutableLiveData<String> = MutableLiveData()
    var name: LiveData<String> = mName
    fun setName(name :String){
        mName.postValue(name)
    }

    private var mCityName: MutableLiveData<String> = MutableLiveData()
    var cityName: LiveData<String> = mCityName
    fun setCityName(cityName :String){
        mCityName.postValue(cityName)
    }

    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, res: Int){
        imageView.setImageResource(res)
    }
}
